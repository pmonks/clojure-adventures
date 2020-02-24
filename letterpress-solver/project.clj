;
; Copyright © 2012-2016 Peter Monks (pmonks@gmail.com)
;
; This work is licensed under the Creative Commons Attribution-ShareAlike 3.0
; Unported License. To view a copy of this license, visit
; http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to Creative
; Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
;

(defproject letterpress-solver "0.1.0-SNAPSHOT"
  :min-lein-version "2.9.1"
  :description "A word finder for a variety of word games such as LetterPress and Scrabble."
  :url "https://github.com/pmonks/clojure-adventures/tree/master/letterpress-solver"
  :license {:name "Creative Commons Attribution-ShareAlike 3.0 Unported License."
            :url "http://creativecommons.org/licenses/by-sa/3.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/tools.cli "1.0.194"]]
  :profiles {:dev {:dependencies [[midje "1.9.9"]]
                   :plugins      [[lein-midje "3.2.1"]]}}
  :main letterpress-solver.core
  :aot [letterpress-solver.core])
