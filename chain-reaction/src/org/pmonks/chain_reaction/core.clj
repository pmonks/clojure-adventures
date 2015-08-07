(ns org.pmonks.chain-reaction.core)

(defn new-cell
  ([] (new-cell nil 0))
  ([owner count]
   (if (zero? count)
     { :owner nil   :count 0     }
     { :owner owner :count count })))

(def blank-cell (new-cell))

(defn new-board
  ([]      (new-board 8 8 nil))
  ([w h]   (new-board w h nil))
  ([w h c] { :width  w
             :height h
             :cells  (if (nil? c) {} c) }))

(defn board-width
  [board]
  (:width board))

(defn board-height
  [board]
  (:height board))

(defn board-cells
  [board]
  (:cells board))

(defn legal-coords?
  [board [x y]]
  (and (>= x 0)
       (<  x (board-width  board))
       (>= y 0)
       (<  y (board-height board))))

(defn all-coords
  [board]
  (for [x (range (board-width board)) y (range (board-height board))] [x y]))

(defn get-cell
  [board coords]
  {:pre [ (legal-coords? board coords) ]}
  (if-let [cells (board-cells board)]
    (if-let [cell (get cells coords)]
      cell
      blank-cell)
    blank-cell))

(defn owner
  ([board coords]
   (owner (get-cell board coords)))
  ([cell]
   (:owner cell)))

(defn unowned?
  ([board coords]
   (unowned? (get-cell board coords)))
  ([cell]
   (nil? (owner cell))))

(defn cell-count
  ([board coords]
   (cell-count (get-cell board coords)))
  ([cell]
   (:count cell)))

(defn number-of-neighbours
  [board [x y]]
  (- 4 (if (= x 0)                          1 0)
       (if (= x (dec (board-width board)))  1 0)
       (if (= y 0)                          1 0)
       (if (= y (dec (board-height board))) 1 0)))

(defn full?
  [board coords]
  (>= (cell-count board coords) (number-of-neighbours board coords)))

(defn legal-move?
  [board player coords]
  (if (legal-coords? board coords)
    (let [cell (get-cell board coords)]
      (or (unowned? cell)
          (= player (owner cell))))
    false))

(defn neighbours
  [board coords]
  (comment "####TODO!!!!"))

(defn explode-full-cell
  [board coords]
  {:pre [ (full? board coords) ]}
  (let [owner          (owner                board coords)
        cell-count     (cell-count           board coords)
        num-neighbours (number-of-neighbours board coords)
        neighbours     (neighbours           board coords)
        new-cell       (new-cell owner (- cell-count num-neighbours))]
    (comment "####TODO!!!!")))

(defn any-full-cells?
  [board]
  (some true? (map #(full? board %) (all-coords board))))

(defn find-full-cell
  "Find the first full cell on the board."
  [board]
  {:pre [ (any-full-cells? board) ]}
  (let [cell-coords (all-coords board)]
    (loop [board         board
           cell-to-check (first cell-coords)
           other-cells   (rest  cell-coords)]
      (if (full? board cell-to-check)
        cell-to-check
        (recur board (first other-cells) (rest other-cells))))))

(defn explode-full-cells
  "Recursively explodes any full cells in the board until there are no full cells on the board."
  [board]
  (loop [board board]
    (if (any-full-cells? board)
      (recur (explode-full-cell board (find-full-cell board)))
      board)))


(defn place-piece
  "Place a piece at the given location on the board, returning the new board."
  [board player [x y]]
  {:pre [ (legal-move? board player [x y]) ]}
  (explode-full-cells (new-board (board-width  board)
                                 (board-height board)
                                 (assoc (board-cells board) [x y] (new-cell player (inc (cell-count board [x y])))))))


