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


(defn stop-editing [cur]
  (swap! cur dissoc :editing))

(defn update-title-text [event cur]
  (swap! cur assoc :title (.. event -target -value)))

(defn insert-new-card [colcur]
  (swap! colcur update :cards conj {:id (random-uuid)
                                    :title ""
                                    :editing true}))

(defn Card [cur]
  (let [card @cur]
    (if (:editing card)
      [:div.card.editing [:input {:type "text"
                                  :value (:title card)
                                  :on-blur #(stop-editing cur)
                                  :on-change #(update-title-text % cur)
                                  :on-key-press #(if (= (.. % -charCode) 13)
                                                   (stop-editing cur))
                                  :autoFocus true}]]
      [:div.card {:on-click #(swap! cur assoc :editing true)} (:title card)])))

(defn NewCard [colcur]
  [:div.new-card
   {:on-click #(insert-new-card colcur)}
   "+ add new card"])

(defn Column [colcur]
  (let [{:keys [title cards editing]} @colcur]
    [:div.column
     (if editing
       [:input {:type "text" :value title}]
       [:h2 title])
     (for [i (-> cards count range)
           :let [k (-> cards (get i) :id)]]
       ^{:key k} [Card (r/cursor colcur [:cards i])])
     [NewCard colcur]]))

(defn NewColumn []
  [:div.new-column
   "+ add new column"])

(defn Board [app-state]
  (let [{:keys [columns]} @app-state]
    [:div.board
     (for [i (-> columns count range)
           :let [k (-> columns (get i) :id)]]
       ^{:key k} [Column (r/cursor app-state [:columns i])])
     [NewColumn ]]))

(r/render [Board app-state] (js/document.getElementById "app"))
