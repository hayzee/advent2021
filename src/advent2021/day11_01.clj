(ns advent2021.day11-01)

(def FILE-NAME "resources/day11-input.txt")

(def FILE-NAME-TEST "resources/day11-input-test.txt")

(defn file-data
  ; Read file data and return a vector of strings for each row.
  [file-name]
  (clojure.string/split (slurp file-name) #"\r\n"))

(defn- file-data-row->grid-row
  ; Convert a file row (a string) to a vector of integer digits
  [file-data-row]
  (mapv #(Character/digit ^char % 10) file-data-row))

(defn file->grid
  ; Create a grid of octopi from the file-data
  [file-data]
  (mapv file-data-row->grid-row file-data))

(defn inc-octopus
  ; Increment a single octopus (an integer), when it reaches 10, set it to zero.
  [octopus]
  (if (= octopus 9)
    0
    (inc octopus)))

(defn inc-grid
  ; Increment a grid of octopi from the file-data by calling inc-octopus on each element.
  [grid]
  (doto
    (mapv #(mapv inc-octopus %) grid)))

(defn find-zeros
  ; Search the grid for 0 and return all [r c] pairs.
  [grid]
  (for [r (range (count grid))
        c (range (count (first grid)))
        :when (zero? (get-in grid [r c]))]
    [r c]))

(defn- surrounds-coords
  ; Find all the coordinates that surround the position
  ; [r c] including diagonals, but excluding anything
  ; off the board.
  [[r c]]
  (let [!neg? (complement neg?)]
    (vec (for [rw (range (dec r) (inc (inc r)))
               cl (range (dec c) (inc (inc c)))
               :when (and (not= [r c] [rw cl])
                          (!neg? rw)
                          (!neg? cl)
                          (< rw 10)
                          (< cl 10))]
           [rw cl]))))

(defn- surrounds-map [grid [r c]]
  ; Given a grid and a sequence of coordinates, return a map of the values within the grid
  ; at those coordinates along with the coordinates as a sequence of maps. Each map looks like
  ; [{:coord [x1 y1] :value v1} ...... {:coord [xn yn] :value vn}].
  (map
    #(hash-map :coord %1 :value (get-in grid %1))
    (surrounds-coords [r c])))


;; 1. inc the grid
;; 2. find zeros - by co-ord
;; 3. find their non-zero and valid surrounding values - including repeats
;; 4. increment those values
;; 5.

(let [grid [[7 6 0 5 3 6 5 4 4 5]
            [4 9 6 7 0 7 6 9 3 3]
            [7 4 8 6 7 7 8 3 9 5]
            [8 3 6 3 5 5 8 3 6 8]
            [8 5 7 9 5 0 7 6 9 0]
            [6 3 8 9 7 4 6 8 6 7]
            [4 3 9 8 0 6 3 9 4 3]
            [8 0 0 4 0 0 3 3 5 6]
            [6 0 6 8 0 6 0 7 7 6]
            [7 4 0 5 9 7 3 7 4 8]]]
  (vec (sort (map
               :coord
               (filter
                 #(and
                    (:value %)
                    (not (zero? (:value %))))
                 (set
                   (mapcat
                     (partial surrounds-map grid)
                     (find-zeros grid))))))))

(->
  (file->grid (file-data FILE-NAME-TEST))
  inc-grid
  inc-grid)


;; FAIL 2

(def simple-grid-0
  [[1 1 1 1 1]
   [1 9 9 9 1]
   [1 9 1 9 1]
   [1 9 9 9 1]
   [1 1 1 1 1]])


(defn all-coords [grid]
  (vec
    (for [r (range (count grid))
          c (range (count (first grid)))]
      [r c])))

; test
(all-coords simple-grid-0)

(defn non-zero-surrounds [grid [r c]]
  (let [grid-width (dec (count (first grid)))]
    (vec
      (for [rw (range (dec r) (+ r 2))
            cl (range (dec c) (+ c 2))
            :when (and
                    (<= 0 rw grid-width)
                    (<= 0 cl grid-width)
                    (not= [rw cl] [r c])
                    (not (= 0 (get-in grid [rw cl]))))]
        [rw cl]))))

(non-zero-surrounds simple-grid-0 [0 0])
(non-zero-surrounds simple-grid-0 [2 2])
(non-zero-surrounds simple-grid-0 [4 4])

(defn process-coords
  [grid coords]
  (if (seq coords)
    (let [coord (first coords)
          new-octopus (inc-octopus (get-in grid coord))
          append-coords (when (zero? new-octopus)
                          (non-zero-surrounds grid coord))]
      (when append-coords (println "append" coord "coords" append-coords))
      (recur
        (update-in grid coord (constantly new-octopus))
        ;        (apply conj (subvec coords 1) append-coords)
        (subvec coords 1)))
    grid))

(map
  println
  (process-coords simple-grid-0 (all-coords simple-grid-0)))



(def simple-grid-0
  [[1 1 1 1 1]
   [1 9 9 9 1]
   [1 9 1 9 1]
   [1 9 9 9 1]
   [1 1 1 1 1]])

(defn rows
  [grid]
  (count grid))

(defn cols
  [grid]
  (count (first grid)))

(defn grid-size
  ; Return the size of the grid as [r c]
  [grid]
  [(rows grid) (cols grid)])

(defn all-coords
  [grid]
  (for [rw (range (rows grid))
        cl (range (cols grid))]
    [rw cl]))

(defn inc-grid
  [grid]
  (reduce (fn [a e] (update-in a e inc)) grid (all-coords grid)))

(inc-grid simple-grid-0)

(defn surrounds-coords
  ; All coords that surround the [r c] element which are valid and don't contain a flasher (10)
  [grid [r c]]
  (for [rw (range (dec r) (+ 2 r))
        cl (range (dec c) (+ 2 c))
        :when (and
                (not= [rw cl] [r c])
                (int? (get-in grid [rw cl]))
                (#{0 10} (get-in grid [rw cl]))
                )]
    [rw cl]))

(surrounds-coords simple-grid-0 [0 0])
(surrounds-coords simple-grid-0 [1 1])
(surrounds-coords simple-grid-0 [4 4])
(surrounds-coords (inc-grid simple-grid-0) [0 0])
(surrounds-coords (inc-grid simple-grid-0) [1 1])
(surrounds-coords (inc-grid simple-grid-0) [4 4])

(defn find-flashers [grid]
  (filter #(= 10 (get-in grid %)) (all-coords grid)))

(find-flashers (inc-grid simple-grid-0))