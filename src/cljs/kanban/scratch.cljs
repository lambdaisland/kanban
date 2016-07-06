(ns kanban.scratch
  (:require [reagent.core :as r]))

(defn Form1Component [x y z]
  [:div.f1 x y z])

(defn Form2Component [x y z]
  (fn [x y z]
    [:div.f2 x y z]))

(defn Form3Component [x y z]
  (r/create-class {:display-name "Form3Component"
                   :reagent-render (fn [x y z]
                                     [:div.f3 x y z])}))
