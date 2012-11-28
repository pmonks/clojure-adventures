;
; Copyright © 2012 Peter Monks (pmonks@gmail.com)
;
; This work is licensed under the Creative Commons Attribution-ShareAlike 3.0
; Unported License. To view a copy of this license, visit
; http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to Creative
; Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
;

(ns letterpress-solver.core
  (:require [clojure.string :as s]
            [letterpress-solver.solver :as solver])
  (:gen-class))

(def word-source         "/usr/share/dict/words")
(def default-num-results 5)

(defmacro my-time
  "Evaluates expr and prints the time it took.  Returns the value of expr.  Minor modification of clojure.core/time."
  [expr]
  `(let [start# (. System (nanoTime))
         ret# ~expr]
     (println (str (/ (double (- (. System (nanoTime)) start#)) 1000000.0) "ms"))
     ret#))

(defn- solve
  [characters words num-results]
  (do
    (print "Finding matching words (original)... ")
    (flush)
    (println (take num-results (my-time (solver/matching-words characters words))))
    (print "Finding matching words (precalculated character frequencies)... ")
    (flush)
    (println (take num-results (my-time (solver/matching-words2 characters words))))
    (print "Generating anagrams... ")
    (flush)
    (let [anagrams (my-time (solver/anagrams words))]
      (print "Finding matching words (anagrams)... ")
      (flush)
      (println (take num-results (my-time (solver/matching-words-from-anagrams characters anagrams)))))
  ))

(defn -main
  "Finds all words that can be constructed from a string of characters, without reusing letters.
   Can be used to cheat at games like Scrabble, LetterPress, etc."
  [& args]
  (if (< (count args) 1)
    (println "You must provide the list of characters to make words from.")
    (let [characters    (first args)
          words         (do
                           (print (str "Loading " word-source "... "))
                           (flush)
                           (my-time (s/split (slurp word-source) #"\s+")))
          num-words-str (first (rest args))]
      (if (nil? num-words-str)
        (solve characters words default-num-results)
        (solve characters words (Integer/parseInt num-words-str))))))
