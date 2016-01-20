(defproject org.pmonks/chain-reaction "0.1.0-SNAPSHOT"
  :description "Chain Reaction"
  :url "https://github.com/pmonks/clojure-adventures/tree/master/chain-reaction"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.4.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [jansi-clj           "0.1.0"]]
  :profiles {:dev {:dependencies [[midje          "1.8.3"]
                                  [clj-ns-browser "1.3.1"]]
                   :plugins      [[lein-midje "3.2"]]}})
