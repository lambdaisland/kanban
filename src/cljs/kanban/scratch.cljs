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
