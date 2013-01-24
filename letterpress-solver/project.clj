;
; Copyright © 2012-2013 Peter Monks (pmonks@gmail.com)
;
; This work is licensed under the Creative Commons Attribution-ShareAlike 3.0
; Unported License. To view a copy of this license, visit
; http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to Creative
; Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
;

(defproject letterpress-solver "0.1.0-SNAPSHOT"
  :description "A word finder for a variety of word games such as LetterPress and Scrabble."
  :url "https://github.com/pmonks/clojure-adventures/tree/master/letterpress-solver"
  :license {:name "Creative Commons Attribution-ShareAlike 3.0 Unported License."
            :url "http://creativecommons.org/licenses/by-sa/3.0/"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/tools.cli "0.2.2"]]
;  :profiles {:dev {:dependencies [[midje "1.4.0"]
  :profiles {:dev {:dependencies [[midje "1.5-alpha8"]
                                  [com.stuartsierra/lazytest "1.2.3"]]
                   :plugins [[lein-midje "2.0.4"]]}}
;                   :plugins [[lein-midje "3.0-alpha1"]]}}
  :repositories {"stuart" "http://stuartsierra.com/maven2"} ; For lazytest
  :main letterpress-solver.core)
