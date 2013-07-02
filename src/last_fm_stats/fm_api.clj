(ns last-fm-stats.fm-api
  (:require [clj-http.client :as client]
            [last-fm-stats.redis :as redis]
            [clojure.string :as str]
            [clojure.data.json :as json]))

(def api-key "5a418c9ce718f3aa7567f487df1ab7da")

;; taste compare

(defn get-taste-compare
  "Returns full JSON response."
  [user1 user2]
  (println "Making API request...")
  (let [url (str "http://ws.audioscrobbler.com"
                 "/2.0/?method=tasteometer.compare"
                 "&type1=user&type2=user"
                 "&value1=" user1
                 "&value2=" user2
                 "&api_key=" api-key
                 "&format=json")
        json-str (:body (client/get url))]
    (json/read-str json-str :key-fn keyword)))

(defn taste-compare
  "Returns hash-map of only what we care about.
   JSON cached in Redis and then parsed into hash-map."
  [user1 user2]
  (let [redis-key (str/lower-case (str user1 "-" user2))
        json (->> (get-taste-compare user1 user2)
                  (redis/memoize redis-key)
                  :comparison)]
    (let [score (-> json :result :score read-string)
          artists (-> json :result :artists :artist)
          users (map :name (-> json :input :user))]
      {:score score
       :artists (sort-by :name artists)
       :users users})))

;; neighbors

(defn get-neighbor-usernames
  "Returns usernames of user's neighbors.
   Cached in Redis with key \"neighbors:`user`\""
  [user]
  (let [url (str "http://ws.audioscrobbler.com/1.0/user/"
                 user
                 "/neighbours.txt")
        parse-response (fn [body] (->> (str/split body #",")
                                       (map str/trim-newline)
                                       (remove str/blank?)))
        redis-key (str/lower-case (str "neighbors:" user))
        usernames (->> (:body (client/get url))
                       (parse-response)
                       (redis/memoize redis-key))]
    usernames))

(defn neighbors
  "Returns maps representing this user's neighbors.
   Each map includes: {:score 0 to 1
                       :artists [artists in common]
                       :users [`user` neighbor]}
   Neighbors are sorted by score desc." 
  [user]
  (let [neighbor-usernames (get-neighbor-usernames user)
        neighbors (pmap taste-compare
                        (repeat user)
                        neighbor-usernames)]
    (reverse (sort-by :score neighbors))))
