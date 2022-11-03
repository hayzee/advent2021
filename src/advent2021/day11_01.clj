(ns advent2021.day11-01)


(defn file-data
  "Read file data and return a vector of strings for each row."
  [file-name]
  (clojure.string/split (slurp file-name) #"\r\n"))

(defn numberify-row
  [row]
  (mapv #(Character/digit ^char % 10) row))

(defn file-data->number-grid
  [file-data]
  (mapv numberify-row file-data))

(def grid (file-data->number-grid (file-data FILE-NAME-TEST)))

(defn inc-row [row]
  (mapv (fn [e] (if (= e 9) 0 (inc e))) row))

(defn inc-grid [grid]
  (mapv inc-row grid))

(defn ncols
  [grid]
  (count (first grid)))

(defn nrows
  [grid]
  (count grid))

(defn surrounding-coords
  [[r c] grid]
  (vec
    (for [rw (range (dec r) (+ r 2))
          cl (range (dec c) (+ c 2))
          :when (and (<= 0 rw (nrows grid))
                     (<= 0 cl (ncols grid))
                     (not= [rw cl] [r c]))]
      [rw cl])))

(defn inc-flasher-neighbour
  [e]
  (if (= e 0)
    0
    (if (= e 9)
      0
      (inc e))))

(defn update-coords
  [grid v-coords update-fn]
  (reduce (fn [g c] (update-in g c update-fn)) grid v-coords))

