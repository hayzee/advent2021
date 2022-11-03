(ns advent2021.day11-01)

(defn file-data
  "Read file data and return a vector of strings for each row."
  [file-name]
  (clojure.string/split (slurp file-name) #"\r\n"))

(defn file-data->number-grid
  [file-data]
  (let [numberify-row (fn [row]
                        (mapv #(Character/digit ^char % 10) row))]
   (mapv numberify-row file-data)))

; todo - remove this
; (def ^:private FILE-NAME-TEST "resources/day11-input-test.txt")
; (def grid (file-data->number-grid (file-data FILE-NAME-TEST)))

(defn ncols
  [grid]
  (count (first grid)))

(defn nrows
  [grid]
  (count grid))

(defn surrounding-coords
  [grid [r c]]
  (vec
   (for [rw (range (dec r) (+ r 2))
         cl (range (dec c) (+ c 2))
         :when (and (<= 0 rw (dec (nrows grid)))
                    (<= 0 cl (dec (ncols grid)))
                    (not= [rw cl] [r c]))]
     [rw cl])))

(defn find-in-grid
  ([grid]
   (find-in-grid grid nil))
  ([grid n]
   (vec
    (for [r (range (nrows grid))
          c (range (ncols grid))
          :when (or (not n)
                    (= n (get-in grid [r c])))]
      [r c]))))

(defn inc-board-square
  [e]
  (if (= e 9)
    0
    (inc e)))

(defn inc-flasher-neighbour
  [e]
  (if (= e 0)
    0
    (inc-board-square e)))

(defn- update-grid
  [grid v-coords update-fn]
  (reduce (fn [g c] (update-in g c update-fn)) grid v-coords))

(defn inc-squares [grid]
 (update-grid grid (find-in-grid grid) inc-board-square))

(defn inc-flashers [grid]
  (update-grid grid
               (mapcat (partial surrounding-coords grid) (find-in-grid grid 0))
               identity))

(defn make-move
  [grid]
  (-> grid
      inc-squares
      inc-flashers))
