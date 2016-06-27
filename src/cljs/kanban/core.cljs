(ns kanban.core
  (:require [reagent.core :as r]))

(enable-console-print!)

(def app-state
  (r/atom {:columns [{:title "Todos"
                      :cards [{:title "Learn about Reagent"}
                              {:title "Tell my friends about Lambda Island"}]}]}))


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

(defn Board [state]
  [:div.board
   (for [c (:columns @state)]
     [Column c])
   [NewColumn]])

(r/render [Board app-state] (js/document.getElementById "app"))
