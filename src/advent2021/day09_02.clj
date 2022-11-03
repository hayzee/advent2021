(ns advent2021.day09-02)

(def FILE-NAME "resources/day09-input.txt")

(def FILE-NAME-TEST "resources/day09-input-test.txt")

(defn file-data
  ; Read file data and return a vector of strings for each row.
  [file-name]
  (clojure.string/split (slurp file-name) #"\r\n"))

(defn file-data->height-map
  [file-data]
  (mapv (fn [r]
          (mapv #(Character/digit ^char % 10) r))
        file-data))

;(defn heightmap [file-name]
;  (->> file-name
;       file-data
;       file-data->height-map))

(defn surrounds-coords
  [[r c]]
  (->>
   [[r c]
    [r (dec c)]
    [(dec r) c]
    [r (inc c)]
    [(inc r) c]]))

(defn surrounds
  [hm [r c]]
  (->>
   (mapv #(get-in hm %) (surrounds-coords [r c]))
   (remove nil?)))

(defn minima? [hm [r c]]
  (let [[f & r] (surrounds hm [r c])]
    (< f (apply min r))))

(defn find-minima-coords
  [hm]
  (for [r (range (count hm))
        c (range (count (first hm)))
        :when (minima? hm [r c])]
    [r c]))

(defn find-minima
  [hm]
  (map #(get-in hm %) (find-minima-coords hm)))

(defn risk-level
  [hm]
  (reduce + (map inc (find-minima hm))))

; Test answer: 15 - correct
(risk-level (file-data->height-map (file-data FILE-NAME-TEST)))

; Actual Answer: 489 - correct
(risk-level (file-data->height-map (file-data FILE-NAME)))

;; Part 2 starts here:
;; Part 2 starts here:
;; Part 2 starts here:
;; Part 2 starts here:
;; Part 2 starts here:

(defn display-basin
  [hm coords]
  (doall
   (map
    #(println (apply str %))
    (reduce
     (fn [a e] (update-in a e (constantly \*)))
     hm
     coords))))

(defn elevate-from [hm level {:keys [visited unvisited] :as visit-map}]
  (if (seq unvisited)
    (elevate-from
     hm
     (inc level)
     {:visited   (reduce conj visited unvisited)
      :unvisited (vec (filter #(and
                                (not (nil? (get-in hm %)))
                                (not= 9 (get-in hm %))
                                (= (inc level) (get-in hm %)))
                              (distinct (mapcat #(surrounds-coords %) unvisited))))})
    (do
      (display-basin hm (:visited visit-map))
      visit-map)))

(defn basin-size
  [hm [r c]]
  (count (:visited (elevate-from hm (get-in hm [r c]) {:visited [] :unvisited #{[r c]}}))))

(defn get-heightmap-basin-size-product
  [hm]
  (->>
   (map #(basin-size hm %) (find-minima-coords hm))
   (sort >)
   (take 3)
   (reduce *)))

(defn basin-size-product [file-name]
  (get-heightmap-basin-size-product (file-data->height-map (file-data file-name))))

; 1134
(basin-size-product FILE-NAME-TEST)

(def hm (file-data->height-map (file-data FILE-NAME-TEST)))

(display-basin hm [[0 1] [0 0] [1 0]])
;[[0 9] [0 8] [1 9] [0 7] [1 8] [2 9] [0 6] [0 5] [1 6]]
;[[2 2] [2 3] [3 2] [1 3] [2 4] [3 3] [3 1] [1 2] [1 4] [2 5] [3 4] [3 0] [2 1] [4 1]]
(display-basin hm [[4 6] [4 5] [3 6] [4 7] [3 7] [4 8] [2 7] [3 8] [4 9]])
; 435666
(basin-size-product FILE-NAME)

;(clojure.pprint/pprint hm)

;(->>
;  (find-minima-coords hm)
;  (map #(basin-size hm %))
;  (sort >)
;  (take 3)
;  (reduce *))
;
