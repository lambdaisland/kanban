(ns kanban.scratch
  (:require [reagent.core :as r]))

(def board (r/atom {:columns [{:cards []}
                              {:cards [{:title "Hello Island!"}]}]}))

(get-in @board [])                  ;;=> {:columns [{:cards []} {:cards [{:title "Hello Island!"}]}]}
(get-in @board [:columns])          ;;=> [{:cards []} {:cards [{:title "Hello Island!"}]}]
(get-in @board [:columns 1])        ;;=> {:cards [{:title "Hello Island!"}]}
(get-in @board [:columns 1 :cards]) ;;=> [{:title "Hello Island!"}]
(get-in @board [:columns 1 :cards 0 :title]) ;;=> "Hello Island!"


board ;;=> #<Atom: {:columns [{:cards []} {:cards [{:title "Hello Island!"}]}]}>

(swap! board assoc-in [:columns 1 :cards 0 :title] "Hello Turtles")

board ;;=> #<Atom: {:columns [{:cards []} {:cards [{:title "Hello Turtles"}]}]}>

(defn set-title! [board col-idx card-idx title]
  (swap! board assoc-in [:columns col-idx :cards card-idx :title] title))

(set-title! board 1 0 "Hello Octopi!")

board ;;=> #<Atom: {:columns [{:cards []} {:cards [{:title "Hello Octopi!"}]}]}>
