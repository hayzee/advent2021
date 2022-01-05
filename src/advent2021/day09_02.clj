(ns advent2021.day09-02)

(defn clear-ns []
  (->>
    (map first (ns-interns *ns*))
    (map #(ns-unmap *ns* %))))

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

(defn heightmap [file-name]
  (->> file-name
       file-data
       file-data->height-map))

(defn surrounds-coords
  [[r c]]
  (->>
    [
     [r c]
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
        :when (minima? hm [r c])
        ]
    [r c]))

(defn find-minima
  [hm]
  (map #(get-in hm %) (find-minima-coords hm)))

(defn risk-level
  [hm]
  (reduce + (map inc (find-minima hm))))

; Test answer
(risk-level (file-data->height-map (file-data FILE-NAME-TEST)))

; Actual Answer
(risk-level (file-data->height-map (file-data FILE-NAME)))

;; Part 2 starts here:
;; Part 2 starts here:
;; Part 2 starts here:
;; Part 2 starts here:
;; Part 2 starts here:


(defn elevate-from [hm level {:keys [visited unvisited] :as visit-map}]
  (if (seq unvisited)
    (elevate-from
      hm
      (inc level)
      {:visited   (reduce conj visited unvisited)
       :unvisited (vec (filter #(and
                                  (= (inc level) (get-in hm %))
                                  (not= 9 (get-in hm %))) (distinct (mapcat #(surrounds-coords %) unvisited))))})
    visit-map))


(defn basin-size
  [hm [r c]]
  (count (:visited (elevate-from hm (get-in hm [r c]) {:visited [] :unvisited #{[r c]}}))))


(defn get-heightmap-basin-size-product
  [hm]
  (->>
    (map #(basin-size hm %) (find-minima-coords hm))
    (sort >)
    #_(take 3)
    #_(reduce *)))

(defn basin-size-product [file-name]
  (get-heightmap-basin-size-product (file-data->height-map (file-data file-name))))

; 1134
(basin-size-product FILE-NAME-TEST)

; 435666
(basin-size-product FILE-NAME)



(def hm (file-data->height-map (file-data FILE-NAME)))

(clojure.pprint/pprint hm)

(->>
  (find-minima-coords hm)
  (map #(basin-size hm %))
  (sort >)
  (take 3)
  (reduce *))

