(ns kanban.scratch
  (:require [reagent.core :as r]))

(def card-atom (r/atom {:title "Hello Island!"}))

(defn set-title! [card title]
  (swap! card (fn [c] (assoc c :title title))))

card-atom ;;=> #<Atom: {:title "Hello Island!"}>

(set-title! card-atom "Hello Turtles!")

card-atom ;;=> #<Atom: {:title "Hello Turtles!"}>
