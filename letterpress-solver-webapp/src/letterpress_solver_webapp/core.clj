;
; Copyright © 2012-2016 Peter Monks (pmonks@gmail.com)
;
; This work is licensed under the Creative Commons Attribution-ShareAlike 3.0
; Unported License. To view a copy of this license, visit
; http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to Creative
; Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
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
