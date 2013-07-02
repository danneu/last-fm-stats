(ns last-fm-stats.handler
  (:use [compojure.core]
        [hiccup core page form element util])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [last-fm-stats.redis :as redis]
            [last-fm-stats.fm-api :as fm-api]
            [last-fm-stats.style :as style]
            [clojure.string :as str]))

;; Html partials

(defn render-artist [{:keys [url name image] :as artist}]
  (let [src (:#text (first (filter #(= (:size %) "small") image)))]
    (html [:li (link-to url name)])))

(defn render-artists [artists]
  (html [:ul.artists (map render-artist artists)]))

(defn render-meter [score]
  (html [:div.meter
         [:span {:style (str "width: " (* score 100) "%")}]]))

(defn render-percent [score]
  (html [:div.percent (.intValue (* score 100))]))

(defn render-user [user]
  (let [url (str "http://www.last.fm/user/" user)]
    (html (link-to {:class "user"} url user))))

(defn render-neighbor [{:keys [score artists users] :as taste}]
  (let [[user1 user2] users]
    (html
     [:div.neighbor
      (render-meter score)
      [:div.info
       (render-user user2)
       (render-artists artists)]
      (render-percent score)])))

(defn render-neighbors
  [user neighbors]
  (html
   [:div.neighbors
    (map render-neighbor neighbors)]))

;; Helpers

(defn link-to-user
  "Link to last.fm profile with username as anchor text."
  [user]
  (let [url (str "http://www.last.fm/user/" user)]
    (link-to url user)))

;; Pages

(defn gen-form []
   (form-to {:class "user-form"} [:get "/neighbors"]
     (label "user" "Username: ")
     (text-field {:class "user-input"
                  :placeholder "Last.fm Username"} "user")
     (submit-button {:class "user-submit"} "Submit")))

(defn gen-layout [page]
  (html5
   [:style style/main]
   page))

(defn gen-neighbor-page [user neighbors]
  (html
   (gen-form)
   [:h1
    (link-to-user user)
    [:span "'s most compatible last.fm neighbors"]]
   [:div.neighbors
    (map render-neighbor neighbors)]))

(defn gen-home-page []
  (html5
   (form-to [:get "/neighbors"]
     (label "user" "Username: ")
     (text-field "user")
     (submit-button "Submit"))))

;; Routes

(defroutes app-routes
  (GET "/" [] (gen-home-page))
  (GET "/neighbors" [user]
       (let [neighbors (fm-api/neighbors user)]
         (gen-layout (gen-neighbor-page user neighbors))))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
