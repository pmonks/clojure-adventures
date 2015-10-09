(ns org.pmonks.chain-reaction.console-test
  (:use midje.sweet)
  (:require [org.pmonks.chain-reaction.core    :refer :all]
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

  )

