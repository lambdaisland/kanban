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

(defn- update-title [cursor title]
  (swap! cursor assoc :title title))

(defn- stop-editing [cursor]
  (swap! cursor dissoc :editing))

(defn- start-editing [cursor]
  (swap! cursor assoc :editing true))

(defn Editable [el cursor]
  (let [{:keys [editing title]} @cursor]
    (if editing
      [el {:className "editing"} [:input {:type "text"
                               :value title
                               :autoFocus true
                               :on-change #(update-title cursor (.. % -target -value))
                               :on-blur #(stop-editing cursor)
                               :on-key-press #(if (= (.-charCode %) 13)
                                                (stop-editing cursor))}]]
      [el {:on-click #(start-editing cursor)} title])))

(defn Card [cursor]
  [Editable :div.card cursor])
(defn add-new-card [col-cur]
  (swap! col-cur update :cards conj {:id (random-uuid)
                                     :title ""
                                     :editing true}))

(defn NewCard [col-cur]
  [:div.new-card
   {:on-click #(add-new-card col-cur)}
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
     ^{:key "new"} [NewCard col-cur]]))

(defn- add-new-column [board]
  (swap! board update :columns conj {:id (random-uuid)
                                     :title ""
                                     :cards []
                                     :editing true}))

(defn NewColumn [board]
  [:div.new-column
   {:on-click #(add-new-column board)}
   "+ add new column"])

(defn Board [board]
  [:div.board
   (map-indexed (fn [idx {id :id}]
                  (let [col-cur (r/cursor board [:columns idx])]
                    ^{:key id} [Column col-cur]))
                (:columns @board))
   ^{:key "new"} [NewColumn board]])

(r/render [Board app-state] (js/document.getElementById "app"))
