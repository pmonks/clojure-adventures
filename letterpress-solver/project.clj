;
; Copyright © 2012-2013 Peter Monks (pmonks@gmail.com)
;
; This work is licensed under the Creative Commons Attribution-ShareAlike 3.0
; Unported License. To view a copy of this license, visit
; http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to Creative
; Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
;

(defproject letterpress-solver "0.1.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :description "A word finder for a variety of word games such as LetterPress and Scrabble."
  :url "https://github.com/pmonks/clojure-adventures/tree/master/letterpress-solver"
  :license {:name "Creative Commons Attribution-ShareAlike 3.0 Unported License."
            :url "http://creativecommons.org/licenses/by-sa/3.0/"}
  :javac-target "1.7"
  :dependencies [
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.3"]
                ]
  :profiles {:dev {:dependencies [
                                  [midje "1.8.3"]
                                 ]}}
  :jvm-opts ^:replace []  ; Stop Leiningen from turning off JVM optimisations - makes it slower to start but ensures code runs as fast as possible
  :main letterpress-solver.core)
