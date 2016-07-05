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

(defn AutoFocusInput [props]
  (r/create-class
   {:display-name  "FocusInput"
    :component-did-mount (fn [component]
                           (.focus (r/dom-node component)))
    :reagent-render (fn [props]
                      [:input props])}))

(defn Editable [cursor element]
  (let [{:keys [title editing] :as item} @cursor]
    (if editing
      [element {:className "editing"}
       [AutoFocusInput {:type "text"
                    :value title
                    :on-blur #(stop-editing cursor)
                    :on-change #(update-title-text % cursor)
                    :on-key-press #(if (= (.. % -charCode) 13)
                                     (stop-editing cursor))}]]
      [element {:on-click #(swap! cursor assoc :editing true)} title])))

(defn Card [cur]
  [Editable cur :div.card])

(defn NewCard [colcur]
  [:div.new-card
   {:on-click #(insert-new-card colcur)}
   "+ add new card"])

(defn Column [colcur]
  (let [{:keys [title cards editing]} @colcur]
    [:div.column
     [Editable colcur :h2]
     (for [i (-> cards count range)
           :let [k (-> cards (get i) :id)]]
       ^{:key k} [Card (r/cursor colcur [:cards i])])
     [NewCard colcur]]))

(defn- insert-new-column [colscur]
  (swap! colscur conj {:id (random-uuid)
                       :title ""
                       :cards []
                       :editing true}))

(defn NewColumn [colscur]
  [:div.new-column
   {:on-click #(insert-new-column colscur)}
   "+ add new column"])

(defn Board [app-state]
  (let [{:keys [columns]} @app-state]
    [:div.board
     (for [i (-> columns count range)
           :let [k (-> columns (get i) :id)]]
       ^{:key k} [Column (r/cursor app-state [:columns i])])
     [NewColumn (r/cursor app-state [:columns]) ]]))

(r/render [Board app-state] (js/document.getElementById "app"))
