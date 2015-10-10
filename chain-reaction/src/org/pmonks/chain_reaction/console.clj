(ns org.pmonks.chain-reaction.console
  (:require [clojure.string                 :as s]
            [jansi-clj.core                 :as jansi]
            [org.pmonks.chain-reaction.core :as cr]))

(def ^:private os-name (System/getProperty "os.name"))

(def is-windows?
  "Are we running on Windows?"
  (.startsWith (.toLowerCase ^String os-name) "windows"))

; Windows console doesn't properly support Unicode #fail
(if is-windows?
  (def numbers [\0 \1 \2 \3 \4 \5 \6 \7 \8 \9])
  (def numbers [\● \➊ \➋ \➌ \➍ \➎ \➏ \➐ \➑ \➒]))   ; \• for zero?

(def number-of-numbers (count numbers))

(def colour-fns [jansi/blue-bright
                 jansi/red-bright
                 jansi/green-bright
                 jansi/yellow-bright
                 jansi/cyan-bright
                 jansi/magenta-bright
                 jansi/white-bright
                 jansi/blue
                 jansi/red
                 jansi/green
                 jansi/yellow
                 jansi/cyan
                 jansi/magenta
                 jansi/white])

(defn init!
  []
  (jansi/enable!))

(def clear-screen-str (str (jansi/erase-screen) (jansi/cursor 1 1)))

(defn ansi-cell-str
  [board coords]
  (if (cr/cell-unowned? board coords)
    (jansi/white (nth numbers 0))
    (let [owner        (cr/cell-owner board coords)
          number       (cr/cell-count board coords)
          colour-index (.indexOf (vec (cr/players board)) owner)
          colour-fn    (nth colour-fns colour-index)]
      (colour-fn (nth numbers number)))))

(defn ansi-board-str
  [board]
  (str clear-screen-str
    (s/join "\n"
      (for [y (range (cr/board-height board))]
        (s/join " "
          (for [x (range (cr/board-width board))]
            (ansi-cell-str board [x y])))))))

(defn board-str
  [board]
  (s/join "\n"
    (for [y (range (cr/board-height board))]
      (s/join " "
        (for [x (range (cr/board-width board))]
          (str (cr/cell-owner board [x y]) "=" (cr/cell-count board [x y])))))))
