(ns advent2021.day04-02)

;;;;; fns to retrieve data ;;;;;;

(def BOARD-SIZE 5)

(defn read-data
  "Get all file data as a vector of strings."
  [file]
  (->>
    (clojure.string/split (slurp file) #"\r\n")))

(defn get-calls
  "Get all the bingo calls as a vector from file-data."
  [file-data]
  (read-string
    (str "[" (first file-data) "]")))

(defn get-next-board
  "Get one board as a vector from file-data."
  [file-data]
  (->>
    (drop 1 file-data)
    (take BOARD-SIZE)
    (map #(str "[" % "]"))
    (apply str)
    (#(str "[" % "]"))
    (read-string)))

(defn get-all-boards
  "Get all the boards vector from file-data."
  [file-data]
  (if (seq file-data)
    (let #_[this-board-data (take (inc BOARD-SIZE) file-data)
            rest-board-data (drop (inc BOARD-SIZE) file-data)]
      [[this-board-data rest-board-data] (split-at (inc BOARD-SIZE) file-data)]
      (conj (get-all-boards rest-board-data) (get-next-board this-board-data)))
    (vector)))

(defn get-boards
  "Exract all the boards from the file-data."
  [file-data]
  (vec (reverse (get-all-boards (drop 1 file-data)))))

;;;;; Helpers ;;;;;;

(defn tee
  "Split a sequence on the basis of a predicate. Consumes all of seq."
  [pred? seq]
  (reduce (fn [[ta fa] e]
            (if (pred? e)
              [(conj ta e) fa]
              [ta (conj fa e)]))
          [[] []]
          seq))

(defn rows
  "Return all the rows from a m by n matrix."
  [matrix]
  matrix)

(defn cols
  "Return all the columns from a m by n matrix."
  [matrix]
  (for [n (range (count (first matrix)))]
    (map #(nth % n) matrix)))

(defn third
  "Grab the third element."
  [s]
  (first (drop 2 s)))

;; Specific

(defn filled?
  "Check if a row or column is filled - i.e. every entry is -ve."
  [row-or-col]
  (every? neg? row-or-col))

(defn update-board
  "Update a board with a single number call."
  [board number]
  (->>
    (apply concat board)
    (mapv #(if (= number %) (- (+ number 100)) %))
    (partition 5)
    (mapv vec)))

(defn update-boards
  "Update all board with a single number call."
  [board-data call-number]
  (map #(update-board % call-number) board-data))

(defn is-winning-board?
  "Check to see if board is a winnign board.
  That is, either 1 row is fully crossed off or 1 column is fully crossed off."
  [board]
  (let [all-rowcols (concat (rows board) (cols board))]
    (boolean (some filled? all-rowcols))))

(defn make-calls
  "Apply all calls to boards in turn, returning a vector of:
  [[call, winning_boards, remaining_boards] .... ]."
  [boards calls]
  (reductions
    (fn [[_ winners boards] call]
      (let [[this_winners this_boards] (tee is-winning-board? (update-boards boards call))]
        (vector call (vec (concat winners this_winners)) this_boards)))
    [:start [] boards]
    calls))

(defn play-bingo
  "Read a bingo file and play the game - returning all moves and positions."
  [file-name]
  (let
    [file-data (read-data file-name)
     boards (get-boards file-data)
     calls (get-calls file-data)]
    (make-calls boards calls)))

(defn display-board
  "Handy for debug."
  [board]
  (for [row board]
    (do
      (print "[")
      (doall (for [cell row]
               (printf "%5d" cell)))
      (println "]"))))

(defn score-move
  "Get the score for a single move (i.e. a single entry returned from play-bingo).
  Assumes a winning move is passed-in, although all moves will return a score."
  [[call winners _]]
  (* call (apply + (filter pos? (apply concat (last winners))))))

(defn first-winner
  "Get the first winner"
  [file-name]
  (first
    (drop-while
      #(empty? (second %))
      (play-bingo file-name))))

(defn last-winner
  "Get the last winner"
  [file-name]
  (first
    (drop-while
      #(not-empty (third %))
      (play-bingo file-name))))

; answer 1 test: 4512
(score-move (first-winner "resources/day4-input-test.txt"))

; answer 2 test: 1924
(score-move (last-winner "resources/day4-input-test.txt"))


; answer 1: 89001
(score-move (first-winner "resources/day4-input.txt"))

; answer 2: 7296
(score-move (last-winner "resources/day4-input.txt"))
