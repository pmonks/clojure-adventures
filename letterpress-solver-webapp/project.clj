;
; Copyright © 2012-2013 Peter Monks (pmonks@gmail.com)
;
; This work is licensed under the Creative Commons Attribution-ShareAlike 3.0
; Unported License. To view a copy of this license, visit
; http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to Creative
; Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
;

(defproject letterpress-solver-webapp "0.1.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :description "Webapp for LetterPress Solver"
  :url "https://github.com/pmonks/clojure-adventures/tree/master/letterpress-solver-webapp"
  :license {:name "Creative Commons Attribution-ShareAlike 3.0 Unported License."
            :url  "http://creativecommons.org/licenses/by-sa/3.0/"}
  :dependencies [
                 [org.clojure/clojure    "1.5.1"]
                 [org.clojure/core.cache "0.6.3"]
                 [http-kit               "2.0.0"]
                 [compojure              "1.1.5"]
                 [hiccup                 "1.0.3"]
                 [ring/ring-core         "1.2.0-beta2"]
                 [ring/ring-devel        "1.2.0-beta2"]
                 [environ                "0.4.0"]
                 [letterpress-solver     "0.1.0-SNAPSHOT"]
                ]
  :plugins [[lein-ring "0.8.3"]]
  :ring {:handler letterpress-solver-webapp.handler/app}
  :profiles
    {:dev {:dependencies [
                          [ring-mock "0.1.3"]
                         ]}}
  :main letterpress-solver-webapp.core)