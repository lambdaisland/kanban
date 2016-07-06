(ns kanban.core
  (:require [reagent.core :as r]))

(enable-console-print!)

(def app-state
  (r/atom {:columns [{:title "Todos"
                      :cards [{:title "Learn about Reagent"}
                              {:title "Tell my friends about Lambda Island"}]}
                     {:title "Awesomize"
                      :cards [{:title "Meditate"}
                              {:title "Work out"}]}]}))

(defn Card [card-cur]
  (let [card @card-cur]
    (if (:editing card)
      [:div.card.editing [:input {:type "text" :value (:title card)}]]
      [:div.card (:title card)])))

(defn NewCard []
  [:div.new-card
   "+ add new card"])

(defn Column [col-cur]
  (let [{:keys [title cards editing]} @col-cur]
    [:div.column
     (if editing
       [:input {:type "text" :value title}]
       [:h2 title])
     (for [idx (range (count cards))]
       (let [card-cur (r/cursor col-cur [:cards idx])]
         [Card card-cur]))
     [NewCard]]))

(defn NewColumn []
  [:div.new-column
   "+ add new column"])

(defn Board [board]
  [:div.board
   (for [idx (range (count (:columns @board)))]
     (let [col-cur (r/cursor board [:columns idx])]
       [Column col-cur]))
   [NewColumn]])

(r/render [Board app-state] (js/document.getElementById "app"))
