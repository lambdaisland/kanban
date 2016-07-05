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

(defn Card [c]
  (let [card @c]
    (if (:editing card)
      [:div.card.editing [:input {:type "text" :value (:title card)}]]
      [:div.card (:title card)])))

(defn NewCard []
  [:div.new-card
   "+ add new card"])

(defn Column [col]
  (let [{:keys [title cards editing]} @col]
    [:div.column
     (if editing
       [:input {:type "text" :value title}]
       [:h2 title])
     (for [i (-> cards count range)]
       [Card (r/cursor col [:cards i])])
     [NewCard]]))

(defn NewColumn []
  [:div.new-column
   "+ add new column"])

(defn Board [state]
  [:div.board
   (for [i (-> @state :columns count range)]
     [Column (r/cursor state [:columns i])])
   [NewColumn]])

(r/render [Board app-state] (js/document.getElementById "app"))
