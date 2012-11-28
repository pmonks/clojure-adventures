;
; Copyright © 2012 Peter Monks (pmonks@gmail.com)
;
; This work is licensed under the Creative Commons Attribution-ShareAlike 3.0
; Unported License. To view a copy of this license, visit
; http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to Creative
; Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
;

(ns game-of-life.core
  (:use clojure.set)
  (:gen-class))

(defrecord Cell [^Integer x ^Integer y])

(def block #{(Cell. 0 0) (Cell. 1 0)
             (Cell. 0 1) (Cell. 1 1)})

(def beehive #{            (Cell. 1 0) (Cell. 2 0)
               (Cell. 0 1)                         (Cell. 3 1)
                           (Cell. 1 2) (Cell. 2 2)})

(def blinker #{(Cell. 0 0)
               (Cell. 0 1)
               (Cell. 0 2)})

(def glider #{            (Cell. 1 0)
                                      (Cell. 2 1)
              (Cell. 0 2) (Cell. 1 2) (Cell. 2 2)})

(defn- alive?
  "Is the given cell alive in the given generation?"
  [generation
   cell]
  {:pre  [(set?      generation)
          (instance? Cell cell)]
   :post [(instance? Boolean %)]}
  (contains? generation cell))

(defn- neighbours
  "Returns all of the neighbours of a given cell, excluding the cell itself."
  [cell]
  {:pre  [(instance? Cell cell)]
   :post [(set? %)]}
  (let [x (:x cell)
        y (:y cell)]
    #{
       (Cell. (dec x) (dec y)) (Cell. x (dec y)) (Cell. (inc x) (dec y))
       (Cell. (dec x) y)                         (Cell. (inc x) y)
       (Cell. (dec x) (inc y)) (Cell. x (inc y)) (Cell. (inc x) (inc y))
     }))

(defn- number-of-living-neighbours
  "Returns the number of living neighbours of the given cell in the given generation."
  [generation
   cell]
  {:pre  [(set?      generation)
          (instance? Cell cell)]
   :post [(instance? Integer %)]}
  (count (intersection generation (neighbours cell))))

(defn- should-live?
  "Should the given cell live in the next generation?"
  [generation
   cell]
  {:pre  [(set?      generation)
          (instance? Cell    cell)]
   :post [(instance? Boolean %)]}
  (let [living-neighbours (number-of-living-neighbours generation cell)]
   (if (alive? generation cell)
     (or (= 2 living-neighbours) (= 3 living-neighbours))
     (= 3 living-neighbours))))

(defn next-generation
  "Returns the next-generation board, given a generation."
  [generation]
  {:pre  [(set? generation)]
   :post [(set? %)]}
  (set (filter #(should-live? generation %) (set (mapcat neighbours generation)))))

(defn- values
  "The values of the given generation's given dimension."
  [generation
   elem]
  {:pre [(set? generation)]
   :post [(set? %)]}
  (set (map elem generation)))

(defn- extend-by-1
  "Extends a collection of numbers by 1 in each direction (both up and down)."
  [c]
  (let [minimum (apply min c)
        maximum (apply max c)]
    (conj c (dec minimum) (inc maximum))))

(defn- print-generation
  "Prints a generation."
  [generation]
  {:pre  [(set? generation)]}
  (doseq [y (sort (extend-by-1 (values generation :y)))]
    (doseq [x (sort (extend-by-1 (values generation :x)))]
      (if (alive? generation (Cell. x y))
        (print "# ")
        (print ". ")))
    (println)))

(defn- clear-screen
  "Clears the screen, using ANSI control characters."
  []
  (let [esc (char 27)]
    (print (str esc "[2J"))     ; ANSI: clear screen
    (print (str esc "[;H"))))   ; ANSI: move cursor to top left corner of screen

(defn -main
  "Run Conway's Game of Life.
   Note: will run out of memory if the resulting board doesn't reach a steady state."
  [& args]
  (loop [saved-generations #{}
         generation        glider]
    (if (not (contains? saved-generations generation))
      (do
        (clear-screen)
        (print-generation generation)
        (flush)
        (Thread/sleep 250)
        (recur (conj saved-generations generation) (next-generation generation))))))