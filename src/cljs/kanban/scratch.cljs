(ns kanban.scratch
  (:require [reagent.core :as r]))

(def board (r/atom {:columns [{:cards []}
                              {:cards [{:title "Hello Island!"}]}]}))


(r/cursor board [:columns 1])
;;=> #<Cursor: [:columns 1] {:cards [{:title "Hello Island!"}]}>
