;
; Copyright © 2012-2013 Peter Monks (pmonks@gmail.com)
;
; This work is licensed under the Creative Commons Attribution-ShareAlike 3.0
; Unported License. To view a copy of this license, visit
; http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to Creative
; Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
;

(ns letterpress-solver.core
  (:require [clojure.string :as s]
            [clojure.java.io :as io]
            [letterpress-solver.solver :as solver])
  (:use [clojure.tools.cli :only [cli]])
  (:gen-class))

(defmacro my-time
  "Evaluates expr and prints the time it took.  Returns the value of expr.  Minor modification of clojure.core/time."
  [expr]
  `(let [start# (. System (nanoTime))
         ret# ~expr]
     (println (str (/ (double (- (. System (nanoTime)) start#)) 1000000.0) "ms"))
     ret#))

(defn -main
  "Entry point for the LetterPress solver."
  [& args]
  (let [[options args banner] (cli args
                                   ["-a" "--all-letters"       "All letters on the board"]
                                   ["-r" "--required-letters"  "The letters that must appear in the resulting word(s)" :default nil]
                                   ["-n" "--num-results"       "The number of results" :default 100]
                                   ["-d" "--dictionary-source" "The source dictionary to use (may be a file or a URL)" :default "linux.words"]
                                   ["-h" "--help"              "Show help" :default false :flag true])]
    (let [all-letters       (:all-letters       options)
          required-letters  (:required-letters  options)
          num-results       (:num-results       options)
          dictionary-source (:dictionary-source options)
          help              (:help              options)]
      (if (or help (not all-letters))
        (println banner)
        (let [words (do
                      (print (str "Loading " dictionary-source "... "))
                      (flush)
                      (if (= dictionary-source "linux.words")
                        (my-time (s/split (slurp (io/resource dictionary-source)) #"\s+"))
                        (my-time (s/split (slurp dictionary-source) #"\s+"))))]
          (print "Finding all possible words... ")
          (flush)
          (let [all-results (my-time (solver/matching-words all-letters words))]
            (if (nil? required-letters)
              (println (take num-results all-results))
              (do
                (print "Filtering by required letters... ")
                (println (take num-results (my-time (solver/words-containing required-letters all-results))))))))))))

