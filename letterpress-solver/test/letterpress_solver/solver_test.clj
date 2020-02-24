;
; Copyright © 2012 Peter Monks Some Rights Reserved
;
; This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
; To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/ or send a
; letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
;

(ns letterpress-solver.solver-test
  (:use midje.sweet
        letterpress-solver.solver))

; For orsm autotest goodness, run lein midje :autotest

(fact (#'letterpress-solver.solver/get-with-default {:foo "foo", :bar "bar"} :blah "blah") => "blah")
(fact (#'letterpress-solver.solver/get-with-default {:foo "foo", :bar "bar"} :foo  "blah") => "foo")

(fact (#'letterpress-solver.solver/contains-letter-sufficient-times? (frequencies "abcd")  (frequencies "apple") \a) => true)
(fact (#'letterpress-solver.solver/contains-letter-sufficient-times? (frequencies "abcdp") (frequencies "apple") \p) => false)
(fact (#'letterpress-solver.solver/contains-letter-sufficient-times? (frequencies "abcd")  (frequencies "apple") \z) => true)

(fact (#'letterpress-solver.solver/contains-letter-sufficient-times? (frequencies "apple")  (frequencies "ae") \a) => true)
(fact (#'letterpress-solver.solver/contains-letter-sufficient-times? (frequencies "apple")  (frequencies "ae") \e) => true)
(fact (#'letterpress-solver.solver/contains-letter-sufficient-times? (frequencies "apple")  (frequencies "ap") \p) => true)
(fact (#'letterpress-solver.solver/contains-letter-sufficient-times? (frequencies "apple")  (frequencies "az") \z) => false)

(fact (#'letterpress-solver.solver/contains-letters? "ae" "apple") => true)
(fact (#'letterpress-solver.solver/contains-letters? "ae" "blueberry") => false)
(fact (#'letterpress-solver.solver/contains-letters? "bbrr" "blueberry") => true)
(fact (#'letterpress-solver.solver/contains-letters? "bbrr" "apple") => false)
(fact (#'letterpress-solver.solver/contains-letters? "bbrrr" "blueberry") => false)

(fact (matching-words "" []) => [])
(fact (matching-words "abcd" ["bad" "good"]) => ["bad"])
(fact (matching-words "abcdefgg" ["bad" "good" "gag"]) => ["bad", "gag"])
(fact (matching-words "abcdefgg" ["bad" "good" "gag" "gauge"]) => ["bad", "gag"])

(fact (words-containing "" []) => [])
(fact (words-containing "ae" ["apple" "blueberry"]) => ["apple"])
(fact (words-containing "ae" ["apple" "blueberry" "orange" "pear" "melon"]) => ["orange" "apple" "pear"])
