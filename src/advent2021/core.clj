(ns advent2021.core)

;; day 1

(defn day1-part1 []
  (let [data (slurp "resources/day1-input.txt")]
    (->>
     (clojure.string/split data #"\r\n")
     (map #(Integer/parseInt %))
     (partition 2 1)
     (filter #(apply < %))
     count)))

; 1665
(day1-part1)

(defn day1-part2 []
  (let [data (slurp "resources/day1-input.txt")]
    (->>
     (clojure.string/split data #"\r\n")
     (map #(Integer/parseInt %))
     (partition 3 1)
     (map #(apply + %))
     (partition 2 1)
     (filter #(apply < %))
     count)))

; 1702
(day1-part2)

;; day 2

(defn day2-part1 []
  (let [data (slurp "resources/day2-input.txt")]
    (->>
     (clojure.string/split data #"\r\n")
     (map #(clojure.string/split % #" "))
     (map (fn [[f s]] (case f
                        "forward" [(Integer/parseInt s) 0]
                        "up" [0 (- (Integer/parseInt s))]
                        "down" [0 (Integer/parseInt s)])))
     (apply map +)
     (reduce *))))

; 1762050
(day2-part1)

(defn day2-part2 []
  (let [data (slurp "resources/day2-input.txt")]
    (->>
     (clojure.string/split data #"\r\n")
     (map #(clojure.string/split % #" "))
     (map (fn [[f s]] (case f
                        "forward" [(Integer/parseInt s) 0]
                        "up" [0 (- (Integer/parseInt s))]
                        "down" [0 (Integer/parseInt s)])))
     (reduce
      (fn [[ah ad aa] [eh ed]]
        (vector
         (+ eh ah)                                       ; horiz - eh adds to horiz
         (+ (* eh aa) ad)                                ; depth - eh * aa adds to depth
         (+ ed aa)))                                     ; aim   - ed adds to aim
      [0 0 0])
     butlast
     (apply *))))

; 1855892637
(day2-part2)

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

(defn life-support-rating [oxygen-generator-rating c02-scrubber-rating]
  (* oxygen-generator-rating c02-scrubber-rating))

;; CRUD HERE !!

(defn read-bin-vector [fname]
  (let [data (slurp fname)
        vos (clojure.string/split data #"\r\n")             ; vector of strings
        boundary (quot (count vos) 2)]
    (mapv (fn [vos] (map #(Character/digit ^char % 10) vos)) (seq vos))))
; vector of sequence of char

;; test
(read-bin-vector "resources/day3-input-test.txt")

(defn squish [binvec]
  (apply map + binvec))

; test
(->>
 (read-bin-vector "resources/day3-input-test.txt")
 squish)

(defn gamma-vec [sumvec]
  (mapv #(if (> 2 %) 1 0) sumvec))

(comment

  (gamma-vec (sum-binvec [[1 0 1 1 0] [1 1 1 1 1] [1 0 0 0 0]]))

  (defn day3-part1 []
    (let [data (slurp "resources/day3-input-test.txt")
          vos (clojure.string/split data #"\r\n")            ; vector of strings
          boundary (quot (count vos) 2)]
      (->>
       vos
       (mapv (fn [vos] (map #(Character/digit ^char % 10) (seq vos)))) ; vector of sequence of char
       ;(reduce sumvecs)
       ;(map #(if (< boundary %) \1 \0))
       ;(apply str)
       ;;(apply map vector)  ; Transpose
       ;;(map frequencies)
       ;;(map (partial sort-by second))
       ;;(map #(first (second %)))
       ;;(apply str)
       ;;(vector)
       ;;binstr->int
       )))

  (defn accumulate-vectors [vec-of-vecs])

  (day3-part1)

  (let [binstr->int (fn [binstr]
                      (Integer/parseInt binstr 2))
        reverse-bits (fn [bitstr]
                       (apply str (map {\0 \1 \1 \0} bitstr)))]
    (->>
     (day3-part1)
     (apply map vector)                                     ; Transpose
     ;  (map frequencies)   ; returns seq of maps
     ;(map (partial sort-by second))
     ;(map #(first (second %)))
     ;(apply str)
     ;(vector)
     ;  binstr->int
     )))