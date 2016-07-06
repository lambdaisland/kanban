(ns kanban.core
  (:require [reagent.core :as r]))

(enable-console-print!)

(def app-state
  (r/atom {:columns [{:id (random-uuid)
                      :title "Todos"
                      :cards [{:id (random-uuid)
                               :title "Learn about Reagent"}
                              {:id (random-uuid)
                               :title "Tell my friends about Lambda Island"}]}
                     {:id (random-uuid)
                      :title "Awesomize"
                      :cards [{:id (random-uuid)
                               :title "Meditate"}
                              {:id (random-uuid)
                               :title "Work out"}]}]}))

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
     (map-indexed (fn [idx {id :id}]
                    (let [card-cur (r/cursor col-cur [:cards idx])]
                      ^{:key id} [Card card-cur]))
                  cards)
     ^{:key "new"} [NewCard]]))

(defn NewColumn []
  [:div.new-column
   "+ add new column"])

(defn Board [board]
  [:div.board
   (map-indexed (fn [idx {id :id}]
                  (let [col-cur (r/cursor board [:columns idx])]
                    ^{:key id} [Column col-cur]))
                (:columns @board))
   ^{:key "new"} [NewColumn]])

(r/render [Board app-state] (js/document.getElementById "app"))
