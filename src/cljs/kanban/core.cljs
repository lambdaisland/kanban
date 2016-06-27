(ns kanban.core
  (:require [reagent.core :as r]))

(enable-console-print!)

(defonce app-state (r/atom {:text "Hello Chestnut!"}))

(defn Card []
  [:div.card
   "a card"])

(defn NewCard []
  [:div.new-card
   "+ add new card"])

(defn Column []
  [:div.column
   [:h2 "a column"]])

(defn NewColumn []
  [:div.new-column
   "+ add new column"])

(defn Board []
  [:div.board
   [Column]
   [Column]
   [NewColumn]])

(r/render [Board] (js/document.getElementById "app"))
