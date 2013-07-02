(ns last-fm-stats.style
  (:require [garden.core :refer [css]]))

(def main
  (css
   [:body
    {:font-family "verdana, sans-serif"
     :margin 0
     :background "#555"
     :padding "50px 100px"}]

   [:.user-form
    {:background "#444"
     :font-size "32px"
     :margin "10px 0"
     :padding "10px"
     :color "#bbb"}
    [:label
     {:display "none"}]
    [:input
     {:font-size "32px"
      :margin 0
      :padding "0 5px"
      :line-height "40px"
      :border "5px solid black"
      :height "40px"}]
    [:.user-submit
     {:height "50px"
      :margin "0 0 0 10px"
      :cursor "pointer"
      :background "#2bc253"}
     [:&:hover
      {:background "#5cdb7e"}]]
    [:.user-input
     {:color "#bbb"
      :background "rgba(0, 0, 0, 0.16)"
      }]]

   [:.percent
    {:display "inline-block"
     :position "absolute"
     :font-weight "bold"
     :right 0
     :top "3px"
     :color "black"
     :opacity "0.75"
     :padding "0 5px"}]

   [:h1
    {:margin 0}]

   [:a
    {:color "black"
     :text-decoration "none"}
    [:&:hover
     {:text-decoration "underline"}]]

   [:.artists
    [:li
     [:a
      [:&:hover
       {:background-color "black"}]]]]

   ;; neighbor

   [:.neighbors
    {:border "5px solid black"}]

   [:.neighbor
    {:position "relative"
     :height "25px"
     :border-bottom "5px solid black"}]

   [:.info
    {:position "absolute"
     :top 0}]

   ;; user

   [:.user
    {:padding-left "5px"
     :width "200px"
     :display "inline-block"
     :position "absolute"
     :top 0
     :left 0
     :padding "3px 0 3px 3px"
     }]

   ;; artists

   [:ul.artists
    {:list-style "none"
     :padding "3px 0"
     :margin "0 0 0 200px"
     :display "inline-block"
     :position "relative"
     :left 0
     }
    [:li
     {:display "inline-block"
      :margin-right "5px"
      :line-height "20px"
      :height "20px"
      :overflow "hidden"}
     [:a
      {:color "white"
       :padding "5px"
       :text-decoration "none"
       :background-color "rgba(0, 0, 0, 0.50)"}]
     [:img {}]]]

   ;; meter

   [:.meter {:height "21px" ;20
             :position "relative"
             :background "#555"}
    [:span {:display "block"
            :height "25px"
            :overflow "hidden"
            :background-color "rgb(43, 194, 83)"}]

   [:.neighbor
    [:&:hover
     [:.meter [:span {:background-color "#5cdb7e"}]]]]

   ]))
