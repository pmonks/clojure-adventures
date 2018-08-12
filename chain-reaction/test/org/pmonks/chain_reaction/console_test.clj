(ns org.pmonks.chain-reaction.console-test
  (:use midje.sweet)
  (:require [jansi-clj.core                    :as jansi]
            [org.pmonks.chain-reaction.core    :refer :all]
            [org.pmonks.chain-reaction.console :refer :all]))

(fact "new-board-str"
  (board-str (new-board 2 2)) => "=0 =0\n=0 =0"
  (board-str (new-board))     => "=0 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0"

  (let [board (set-cell (new-board) [0 0] (new-cell :player1 1))]
    (board-str board) => ":player1=1 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0")

  (let [board (set-cell (set-cell (new-board) [0 0] (new-cell :player1 1)) [7 7] (new-cell :player2 1))]
    (board-str board) => ":player1=1 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 =0
=0 =0 =0 =0 =0 =0 =0 :player2=1")
  )

(def ansi-empty-cell-str (jansi/white       "●"))
(def player1-1cell-str   (jansi/blue-bright "➊"))

(fact "ansi-board-str"
  (ansi-board-str (new-board 2 2)) => (str clear-screen-str
                                           ansi-empty-cell-str " " ansi-empty-cell-str
                                           "\n"
                                           ansi-empty-cell-str " " ansi-empty-cell-str)

  (let [board (set-cell (new-board 2 2) [0 0] (new-cell :player1 1))]
    (ansi-board-str board) => (str clear-screen-str
                                   player1-1cell-str " " ansi-empty-cell-str
                                   "\n"
                                   ansi-empty-cell-str " " ansi-empty-cell-str))
  )
