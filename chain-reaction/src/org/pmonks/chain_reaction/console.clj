(ns org.pmonks.chain-reaction.console
  (:require [clojure.string                 :as s]
            [org.pmonks.chain-reaction.core :as cr]))

(defn board-str
  [board]
  (s/join "\n"
    (for [y (range (cr/board-height board))]
      (s/join " "
        (for [x (range (cr/board-width board))]
          (str (cr/cell-owner board [x y]) "=" (cr/cell-count board [x y])))))))
