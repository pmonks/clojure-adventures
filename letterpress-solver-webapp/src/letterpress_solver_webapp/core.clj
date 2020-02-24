;
; Copyright © 2012 Peter Monks Some Rights Reserved
;
; This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
; To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/ or send a
; letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
;

(ns letterpress-solver-webapp.core
  (:gen-class)
  (:require [org.httpkit.server :as http]
            [letterpress-solver-webapp.handler :as handler]
            [environ.core :refer [env]]))

(defn -main
  "Start the server."
  [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (def stop-server (http/run-server #'letterpress-solver-webapp.handler/app {:port port}))
    (println (str "LetterPress Solver Web Server running on port " port "."))))
