(ns last-fm-stats.redis
  (:refer-clojure :exclude [set get memoize])
  (:require [taoensso.carmine :as car]))

;; Connection

(def pool (car/make-conn-pool)) 
(def server-spec (car/make-conn-spec)) 
(defmacro wcar [& body] `(car/with-conn pool server-spec ~@body))

;; Redis methods

(defn all-keys [] (wcar (car/keys "*")))

(defn set [k v] (wcar (car/set k v)))
(defn get [k] (wcar (car/get k)))

;; Helpers

(defmacro memoize
  "Cache expressions in Redis with given `key`."
  [key & body]
  `(if (get ~key)
     (get ~key)
     (do (set ~key ~@body) (get ~key))))
