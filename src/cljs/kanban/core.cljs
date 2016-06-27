(ns kanban.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(defonce app-state (atom {:text "Hello Chestnut!"}))

(defn Card []
  [:div.card])
(defn Column []
  [:div.column])
(defn Board []
  [:div.board])

(reagent/render [greeting] (js/document.getElementById "app"))
