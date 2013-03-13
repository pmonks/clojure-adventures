;
; Copyright © 2012-2013 Peter Monks (pmonks@gmail.com)
;
; This work is licensed under the Creative Commons Attribution-ShareAlike 3.0
; Unported License. To view a copy of this license, visit
; http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to Creative
; Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
;

(defproject letterpress-solver-webapp "0.1.0-SNAPSHOT"
  :description "Webapp for LetterPress Solver"
  :url "https://github.com/pmonks/clojure-adventures/tree/master/letterpress-solver-webapp"
  :license {:name "Creative Commons Attribution-ShareAlike 3.0 Unported License."
            :url  "http://creativecommons.org/licenses/by-sa/3.0/"}
  :dependencies [
                 [org.clojure/clojure    "1.4.0"]
                 [org.clojure/core.cache "0.6.2"]
                 [http-kit               "2.0.0-RC4"]
                 [compojure              "1.1.5"]
                 [hiccup                 "1.0.2"]
                 [letterpress-solver     "0.1.0-SNAPSHOT"]
                 [ring/ring-core         "1.1.8"]
                 [ring/ring-devel        "1.1.8"]
                 [environ                "0.2.1"]
                ]
  :plugins [[lein-ring "0.8.2"]]
  :ring {:handler letterpress-solver-webapp.handler/app}
  :profiles
    {:dev {:dependencies [[ring-mock "0.1.3"]]}}
  :main letterpress-solver-webapp.core)