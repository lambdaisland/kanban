(ns kanban.scratch
  (:require [reagent.core :as r]))

(defn Form1Component [x y z]
  [:div.f1 x y z])

(defn Form2Component [x y z]
  (let [component-state (r/atom {})]
    (request-data-from-api :handler (fn [response] (reset! component-state response)))

    (fn [x y z]
      [:div.f2 x y z])))
