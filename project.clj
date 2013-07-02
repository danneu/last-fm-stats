(defproject last-fm-stats "0.1.0-SNAPSHOT"
  :description "A website for displaying your lastfm.com stats."
  :url "http://github.com/danneu/last-fm-stats"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.3"]
                 [clj-http "0.7.2"]
                 [garden "0.1.0-beta3"]
                 [com.taoensso/carmine "1.12.0"]
                 [org.clojure/data.json "0.2.2"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler last-fm-stats.handler/app}
  :profiles {:dev {:dependencies [[ring-mock "0.1.5"]]}})
