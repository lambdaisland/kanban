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

(defn- update-title [card-cur title]
  (swap! card-cur assoc :title title))

(defn- stop-editing [card-cur]
  (swap! card-cur dissoc :editing))

(defn- start-editing [card-cur]
  (swap! card-cur assoc :editing true))

(defn Card [card-cur]
  (let [{:keys [editing title]} @card-cur]
    (if editing
      [:div.card.editing [:input {:type "text"
                                  :value title
                                  :autoFocus true
                                  :on-change #(update-title card-cur (.. % -target -value))
                                  :on-blur #(stop-editing card-cur)
                                  :on-key-press #(if (= (.-charCode %) 13)
                                                   (stop-editing card-cur))}]]
      [:div.card {:on-click #(start-editing card-cur)} title])))

(defn NewCard []
  [:div.new-card
   "+ add new card"])

(defn Column [col-cur]
  (let [{:keys [title cards editing]} @col-cur]
    [:div.column
     (if editing
       [:input {:type "text" :value title :key "edit"}]
       [:h2 {:key "title"} title])
     (for [idx (range (count cards))]
       (let [card-cur (r/cursor col-cur [:cards idx])]
         ^{:key idx} [Card card-cur]))
     ^{:key "new"} [NewCard]]))

(defn NewColumn []
  [:div.new-column
   "+ add new column"])

(defn Board [board]
  [:div.board
   (for [idx (range (count (:columns @board)))]
     (let [col-cur (r/cursor board [:columns idx])]
      ^{:key idx} [Column col-cur]))
   ^{:key "new"} [NewColumn]])

(r/render [Board app-state] (js/document.getElementById "app"))
