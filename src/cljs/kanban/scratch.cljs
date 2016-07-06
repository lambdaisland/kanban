(ns kanban.scratch
  (:require [reagent.core :as r]))

(def card-atom (r/atom {:title "Hello Island!"}))

(defn set-title! [card title]
  (swap! card (fn [c] (assoc c :title title))))

card-atom ;;=> #<Atom: {:title "Hello Island!"}>

(set-title! card-atom "Hello Turtles!")

card-atom ;;=> #<Atom: {:title "Hello Turtles!"}>


(def board (r/atom {:columns [{:cards []}
                              {:cards [{:title "Hello Island!"}]}]}))

(get-in @board [])                  ;;=> {:columns [{:cards []} {:cards [{:title "Hello Island!"}]}]}
(get-in @board [:columns])          ;;=> [{:cards []} {:cards [{:title "Hello Island!"}]}]
(get-in @board [:columns 1])        ;;=> {:cards [{:title "Hello Island!"}]}
(get-in @board [:columns 1 :cards]) ;;=> [{:title "Hello Island!"}]
(get-in @board [:columns 1 :cards 0 :title]) ;;=> "Hello Island!"
