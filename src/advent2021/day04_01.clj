(ns advent2021.day04-01)

(defn read-data
  ;
  [file]
  (->>
   (clojure.string/split (slurp file) #"\r\n")))

; test - keep for now
;(def file-data (read-data "resources/day4-input-test.txt"))
(def file-data (read-data "resources/day4-input.txt"))

(defn get-bingo-calls
  [file-data]
  (read-string
   (str "[" (first file-data) "]")))

; test
(def bingo-calls (get-bingo-calls file-data))

(defn get-next-board
  [file-data]
  (->>
   (drop 1 file-data)
   (take 5)
   (map #(str "[" % "]"))
   (apply str)
   (#(str "[" % "]"))
   (read-string)))

(defn get-all-boards
  [file-data]
  (if (seq file-data)
    (let [this-board-data (take 6 file-data)
          rest-board-data (drop 6 file-data)]
      (conj (get-all-boards rest-board-data) (get-next-board this-board-data)))
    (vector)))

(defn get-board-data
  [file-data]
  (vec (reverse (get-all-boards (drop 1 file-data)))))

; test
(def board-data (get-board-data file-data))

(defn check-number
  [number board]
  (->>
   (apply concat board)
   (mapv #(if (= number %) (- number) %))
   (partition 5)
   (mapv vec)))

; test
; (def board (first (get-board-data file-data)))
(defn rows
  [board]
  board)

(defn cols
  [board]
  (for [n (range (count (first board)))]
    (map #(nth % n) board)))

(defn filled? [row-or-col]
  (every? neg? row-or-col))

(defn score-board
  [board]
  (reduce + (filter pos? (apply concat (cols board)))))

(defn winning-board?
  [board]
  (let [all-rowcols (concat (rows board) (cols board))]
    (when (some filled? all-rowcols)
      (score-board board))))

(winning-board?
 [[1 -2 3 -4]
  [-1 2 -3 -4]
  [-1 2 3 4]
  [-1 2 3 -4]])

(score-board
 [[1 -2 3 -4]
  [-1 -2 -3 -4]
  [-1 2 3 -4]
  [-1 2 3 -4]])

(defn update-boards
  [boards num]
  (map (partial check-number num) boards))

(defn apply-bingo-calls
  [boards bingo-calls]
  (if (seq bingo-calls)
    (let [this-bingo-call (first bingo-calls)
          updated-boards (update-boards boards this-bingo-call)
          board-score (some winning-board? updated-boards)]
      (if board-score
        {:boards    updated-boards
         :last-call this-bingo-call
         :score     (* this-bingo-call board-score)}
        (apply-bingo-calls
         updated-boards
         (rest bingo-calls))))
    nil))

(apply-bingo-calls board-data bingo-calls)

;; part2

;(reductions (fn [a e] (update-boards a e)) board-data bingo-calls)

(def state {:winning-boards []
            :boards board-data})

(defn update-board
  [board number]
  (->>
   (apply concat board)
   (mapv #(if (= number %) (- number) %))
   (partition 5)
   (mapv vec)))

(defn is-winning-board?
  [board]
  (let [all-rowcols (concat (rows board) (cols board))]
    (some filled? all-rowcols)))

(defn check-board [board]
  {:board board
   :is-winning-board? (is-winning-board? board)})

(def board-data2 board-data)

(map #(check-board (update-board % 1)) board-data2)

(hash-map 1 2)

