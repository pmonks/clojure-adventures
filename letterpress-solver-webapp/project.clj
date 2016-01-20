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
  :javac-target "1.7"
  :dependencies [
                 [org.clojure/clojure    "1.8.0"]
                 [org.clojure/core.cache "0.6.4"]
                 [http-kit               "2.1.19"]
                 [compojure              "1.4.0"]
                 [hiccup                 "1.0.5"]
                 [ring/ring-core         "1.4.0"]
                 [ring/ring-devel        "1.4.0"]
                 [environ                "1.0.1"]
                 [letterpress-solver     "0.1.0-SNAPSHOT"]
                ]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler letterpress-solver-webapp.handler/app}
  :profiles
    {:dev {:dependencies [
                          [ring-mock "0.1.5"]
                         ]}}
  :jvm-opts ^:replace []  ; Stop Leiningen from turning off JVM optimisations - makes it slower to start but ensures code runs as fast as possible
  :main letterpress-solver-webapp.core)