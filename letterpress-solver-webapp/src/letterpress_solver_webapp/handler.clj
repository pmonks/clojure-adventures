;
; Copyright © 2012-2013 Peter Monks (pmonks@gmail.com)
;
; This work is licensed under the Creative Commons Attribution-ShareAlike 3.0
; Unported License. To view a copy of this license, visit
; http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to Creative
; Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
;

(ns letterpress-solver-webapp.handler
  (:use compojure.core)
  (:use ring.middleware.reload)
  (:use ring.middleware.stacktrace)
  (:use hiccup.core)
  (:use hiccup.page)
  (:use hiccup.form)
  (:require [clojure.string            :as s]
            [clojure.core.cache        :as cache]
            [compojure.handler         :as handler]
            [compojure.route           :as route]
            [letterpress-solver.solver :as solver]))

; Cache up to 4 sets of matching words for a given list of letters
(def board-cache (atom (cache/lru-cache-factory {} :threshold 4)))

(def dictionary (s/split (slurp "/usr/share/dict/words") #"\s+"))

(defn page-layout [& content]
  (html5
    [:head
      [:meta {:charset "utf-8"}]
      [:title "LetterPress Solver"]]
    [:body content]))

(defn page-enter-letters
  ([] (page-enter-letters "" "" 100))
  ([all-letters required-letters number-of-results]
   (page-layout
     [:h1 "LetterPress Solver"]
     (form-to {:autocorrect "off", :autocapitalize "off"} [:post "/"]
       (text-field {:placeholder "All Letters", :tabindex 1, :autofocus true, :size 100 } "all-letters" all-letters)
       [:br]
       (text-field {:placeholder "Required Letters (optional)", :tabindex 2, :size 100 } "required-letters" required-letters)
       [:br]
       (text-field {:placeholder "Number of Results", :tabindex 3, :size 20 } "number-of-results" number-of-results) "results"
       [:br]
       (submit-button {:tabindex 4} "Solve")))))

(defn- get-board-cache
  [board-cache all-letters dictionary]
  (if (cache/has? board-cache all-letters)
    (cache/hit  board-cache all-letters)
    (cache/miss board-cache all-letters (solver/matching-words all-letters dictionary))))

(defn matching-words
  [all-letters dictionary]
  (do
    (swap! board-cache get-board-cache all-letters dictionary)
    (cache/lookup @board-cache all-letters)))

(defn controller-solve
  [all-letters required-letters number-of-results]
  (if (nil? all-letters)
    (throw (IllegalArgumentException. "Must provide all-letters"))
    (let [number-of-results (Integer/parseInt number-of-results)
          all-results       (matching-words all-letters dictionary)]
      (if (s/blank? required-letters)
        (take number-of-results all-results)
        (take number-of-results (solver/words-containing required-letters all-results))))))

(defn page-show-results
  [results]
  (page-layout
    [:h1 "Results"]
    [:a {:href "/"} "Solve another one"]
    [:ul
      (for [result results]
        [:li result])]))

(defroutes app-routes
  (GET "/" [all-letters required-letters number-of-results] (page-enter-letters all-letters required-letters (if (s/blank? number-of-results) 100 number-of-results)))
  (POST "/" [all-letters required-letters number-of-results]
    (page-show-results (controller-solve all-letters required-letters number-of-results)))
;  (route/files "/static/")
  (route/not-found "Not Found"))

(def handler
  (handler/site app-routes))

(def app
  (-> #'handler
    (wrap-reload '[letterpress-solver-webapp.handler])
    (wrap-stacktrace)))