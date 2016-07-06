(ns kanban.scratch
  (:require [reagent.core :as r]))

(def board (r/atom {:columns [{:cards []}
                              {:cards [{:title "Hello Island!"}]}]}))


(def cards-cursor
  (r/cursor board [:columns 0 :cards]))

@cards-cursor

(swap! cards-cursor conj {:title "New card in column 0"})

@cards-cursor ;;=> [{:title "New card in column 0"}]
@board ;;=> {:columns [{:cards [{:title "New card in column 0"}]} {:cards [{:title "Hello Island!"}]}]}
