(ns org.pmonks.chain-reaction.core)

(defn new-board
  ([]      (new-board 8 8 nil))
  ([w h]   (new-board w h nil))
  ([w h c] { :width  w
             :height h
             :cells  (if (nil? c) {} c) }))

(defn new-cell
  ([] (new-cell nil 0))
  ([owner count]
   (when-not (zero? count)
    {:owner owner, :count count} )))

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

(defn all-cells
  [board]
  (for [x (range (board-width board)) y (range (board-height board))] [x y]))

(defn occupied-cells
  [board]
  (keys (board-cells board)))

(defn get-cell
  [board coords]
  {:pre [ (legal-coords? board coords) ]}
  (when-let [cells (board-cells board)]
    (when-let [cell (get cells coords)]
      cell)))

(defn set-cell
  [board coords cell]
  {:pre [ (legal-coords? board coords) ]}
  (if (nil? cell)
    (new-board (board-width  board)
               (board-height board)
               (dissoc (board-cells board) coords))
    (new-board (board-width  board)
               (board-height board)
               (assoc (board-cells board) coords cell))))

(defn cell-owner
  ([board coords]
   (cell-owner (get-cell board coords)))
  ([cell]
   (when-not (nil? cell)
    (:owner cell))))

(defn cell-unowned?
  ([board coords]
   (cell-unowned? (get-cell board coords)))
  ([cell]
   (nil? (cell-owner cell))))

(defn cell-count
  ([board coords]
   (cell-count (get-cell board coords)))
  ([cell]
   (if (nil? cell)
    0
    (:count cell))))

(defn number-of-neighbours
  [board [x y]]
  (- 4 (if (= x 0)                          1 0)
       (if (= x (dec (board-width board)))  1 0)
       (if (= y 0)                          1 0)
       (if (= y (dec (board-height board))) 1 0)))

(defn neighbours
  [board [x y]]
  { :pre [ (legal-coords? board [x y]) ]}
  (filter #(legal-coords? board %) [[x       (dec y)]
                                    [(dec x) y]
                                    [(inc x) y]
                                    [x       (inc y)]]))

(defn full?
  [board coords]
  (>= (cell-count board coords) (number-of-neighbours board coords)))

(defn any-full-cells?
  [board]
  (boolean (some true? (map #(full? board %) (occupied-cells board)))))

(defn find-full-cell
  "Find the first full cell on the board, or nil if there aren't any."
  [board]
  (let [cell-coords (occupied-cells board)]
    (loop [board         board
           cell-to-check (first cell-coords)
           other-cells   (rest  cell-coords)]
      (when-not (nil? cell-to-check)
        (if (full? board cell-to-check)
          cell-to-check
          (recur board (first other-cells) (rest other-cells)))))))

(defn explode-full-cell
  [board coords]
  {:pre [ (full? board coords) ]}
  (let [owner          (cell-owner           board coords)
        ccount         (cell-count           board coords)
        num-neighbours (number-of-neighbours board coords)
        neighbours     (neighbours           board coords)]
    (loop [board             (set-cell board coords (new-cell owner (- ccount num-neighbours)))
           current-neighbour (first neighbours)
           other-neighbours  (rest  neighbours)]
      (if (nil? current-neighbour)
        board
        (recur (set-cell board
                         current-neighbour
                         (new-cell owner
                                   (inc
                                      (cell-count board current-neighbour))))
               (first other-neighbours)
               (rest  other-neighbours))))))

(defn explode-full-cells
  "Recursively explodes any full cells in the board until there are no full cells on the board."
  [board]
  (loop [board board]
    (if (any-full-cells? board)
      (recur (explode-full-cell board (find-full-cell board)))
      board)))

(defn legal-move?
  [board player coords]
  (if (legal-coords? board coords)
    (let [cell (get-cell board coords)]
      (or (cell-unowned? cell)
          (= player (cell-owner cell))))
    false))

(defn place-piece
  "Place a piece at the given location on the board, returning the new board."
  [board player coords]
  {:pre [ (legal-move? board player coords) ]}
  (let [new-board (new-board (board-width  board)
                             (board-height board)
                             (assoc (board-cells board) coords (new-cell player (inc (cell-count board coords)))))]
    (explode-full-cells new-board)))

