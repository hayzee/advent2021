(ns advent2021.day11-01)

(def ^:private FILE-NAME "resources/day11-input.txt")

(def FILE-NAME-TEST "resources/day11-input-test.txt")

(defn file-data
  "Read file data and return a vector of strings for each row."
  [file-name]
  (clojure.string/split (slurp file-name) #"\r\n"))

(defn- file-data-row->grid-row
  "Convert a file row (a string) to a vector of integer digits"
  [file-data-row]
  (mapv #(Character/digit ^char % 10) file-data-row))

(defn file->grid
  "reate a grid of octopi from the file-data"
  [file-data]
  (mapv file-data-row->grid-row file-data))

;; solution starts here!

(defn row-count
  "return all the rows in the grid"
  [grid]
  (count grid))

(defn col-count
  "return all the columns in the grid"
  [grid]
  (count (first grid)))

(defn all-coords
  "return all [r c] coordinates of the grid"
  [grid]
  (for [rw (range (row-count grid))
        cl (range (col-count grid))]
    [rw cl]))

(defn inc-grid
  "increment all cells in the grid, returning a new grid"
  [grid]
  (map (fn [rw] (mapv inc rw)) grid))

(defn find-grid
  "search the grid for all elements that satisfy pred?, returning all coords in the grid"
  [grid pred?]
  (filter #(pred? (get-in grid %)) (all-coords grid)))

(defn surrounds-coords
  "return all coords that surround [r c] within grid"
  [grid [r c]]
  (let [num-rows (dec (count grid))
        num-cols (dec (count (first grid)))
        at       (partial get-in grid)]
    (for [rw (range (dec r) (+ 2 r))
          cl (range (dec c) (+ 2 c))
          :when (and
                  (not= [rw cl] [r c])
                  (<= 0 rw num-rows)
                  (<= 0 cl num-cols)
                  (not (contains? #{0 10} (at [rw cl])))
                  )]
      [rw cl])))

(defn flasher?
  [cell]
  "predicate to determine if a the cell is flashing"
  (= 10 cell))

(defn- find-flashers
  [grid]
  "find all the flashers in a grid"
  (find-grid grid flasher?))

(defn get-flasher-adjacents
  "given a grid and a seq of flashers coords, return the set of coords of cells that are
  adjacent to those flashers, which are themselves not flashers (i.e. not 10 or 0)
  "
  [grid]
  (set
    (mapcat
      #(surrounds-coords grid %)
      (find-flashers grid))))

(defn apply-to-grid [grid coords f]
  (reduce
    (fn [a e] (update-in a e f))
    grid
    coords))

(defn grid-step
  [grid]
  (let [igrid (inc-grid grid)
        ifadj (get-flasher-adjacents igrid)]))


; Algorithm - (per step)
; ======================
; 1. increment the grid
; 2. while identify flashers
;     2a. increment-flasher-adjacents
;     2b. set flashers to 0 - but not new flashers from 2a.


;; scratch area

;(let [thegrid (inc-grid simple-grid-0)]
;  (get-flasher-adjacents thegrid (find-flashers thegrid)))
