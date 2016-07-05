(ns kanban.core
  (:require [reagent.core :as r]))

(enable-console-print!)

(defonce app-state
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


(defn Card [cur]
  (let [card @cur]
    (if (:editing card)
      [:div.card.editing [:input {:type "text"
                                  :value (:title card)
                                  :on-blur #(swap! cur dissoc :editing)
                                  :on-change #(swap! cur assoc :title (.. % -target -value))}]]
      [:div.card {:on-click #(swap! cur assoc :editing true)} (:title card)])))

(defn NewCard []
  [:div.new-card
   "+ add new card"])

(defn Column [col]
  (let [{:keys [title cards editing]} @col]
    [:div.column
     (if editing
       [:input {:type "text" :value title}]
       [:h2 title])
     (for [i (-> cards count range)
           :let [k (-> cards (get i) :id)]]
       ^{:key k} [Card (r/cursor col [:cards i])])
     [NewCard]]))

(defn NewColumn []
  [:div.new-column
   "+ add new column"])

(defn Board [app-state]
  (let [{:keys [columns]} @app-state]
    [:div.board
     (for [i (-> columns count range)
           :let [k (-> columns (get i) :id)]]
       ^{:key k} [Column (r/cursor app-state [:columns i])])
     [NewColumn]]))

(r/render [Board app-state] (js/document.getElementById "app"))
