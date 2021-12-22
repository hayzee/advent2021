(ns advent2021.day03)

; day 3 - Part 1

(defn binstr->binvec [s]
  (mapv {\0 0 \1 1} s))

; test
;(binstr->binvec "0101010101")

(defn read-data [file]
  (->>
    (clojure.string/split (slurp file) #"\r\n")
    (mapv binstr->binvec)))

; test
;(read-data "resources/day3-input-test.txt")

(defn gamma-rate [binvec]
  (let [boundary (quot (count binvec) 2)]
    (->>
      (apply map + binvec)
      (map #(if (> % boundary)
              1
              0)))))

; test
;(gamma-rate
;  [[0 0 1 0 0]
;   [1 1 1 1 0]
;   [1 0 1 1 0]
;   [1 0 1 1 1]
;   [1 0 1 0 1]
;   [0 1 1 1 1]
;   [0 0 1 1 1]
;   [1 1 1 0 0]
;   [1 0 0 0 0]
;   [1 1 0 0 1]
;   [0 0 0 1 0]
;   [0 1 0 1 0]])

(defn invert-bits [bits]
  (map {1 0 0 1} bits))

; test
;(invert-bits [1 0 1 1 0])

(defn bin->int [binvec]
  (Integer/parseInt (apply str binvec) 2))

; test
(bin->int [1 0 1 1 0])

;; the answer
(let [gamma-rate (gamma-rate (read-data "resources/day3-input.txt"))]
  (->>
    (vector gamma-rate (invert-bits gamma-rate))
    (map bin->int)
    (reduce *)))


; day 3 - Part 2

(def diagnostic-report (read-data "resources/day3-input.txt"))

(defn group-by-bit [diagnostic-report bitno]
  (group-by #(nth % bitno) diagnostic-report))

;(group-by-bit diagnostic-report 4)

(defn majority-bits [diagnostic-report bitno]
  (let [bit-groups (group-by-bit diagnostic-report bitno)
        ones (bit-groups 1)
        zeros (bit-groups 0)]
    (if (>= (count ones) (count zeros))
      ones
      zeros)))

;(majority-bits diagnostic-report 0)

(defn minority-bits [diagnostic-report bitno]
  (let [bit-groups (group-by-bit diagnostic-report bitno)
        ones (bit-groups 1)
        zeros (bit-groups 0)]
    (if (< (count ones) (count zeros))
      ones
      zeros)))

;(minority-bits diagnostic-report 0)

(defn get-rating [minmaj diagnostic-report bitno]
  (let [itr (minmaj diagnostic-report bitno)]
    (if (<= (count itr) 1)
      (first itr)
      (get-rating minmaj itr (inc bitno)))))

(defn scrubber-rating [diagnostic-report]
  (bin->int (get-rating minority-bits diagnostic-report 0)))

(defn oxygen-generator-rating [diagnostic-report]
  (bin->int (get-rating majority-bits diagnostic-report 0)))

(*
  (scrubber-rating diagnostic-report)
  (oxygen-generator-rating diagnostic-report))