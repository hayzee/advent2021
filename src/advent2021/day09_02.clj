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

(defn surrounds
  [hm [r c]]
  (->>
    [
     (get-in hm [r c])
     (get-in hm [r (dec c)])
     (get-in hm [(dec r) c])
     (get-in hm [r (inc c)])
     (get-in hm [(inc r) c])
     ]
    (remove nil?)))

(defn minima? [hm [r c]]
  (let [[f & r] (surrounds hm [r c])]
    (< f (apply min r))))

(defn find-minima
  [hm]
  (for [r (range (count hm))
        c (range (count (first hm)))
        :when (minima? hm [r c])
        ]
    (get-in hm [r c])))

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

(def hm (file-data->height-map (file-data FILE-NAME-TEST)))

(clojure.pprint/pprint hm)

(defn surround-coords
  [[r c]]
  (->>
    [
     [r c]
     [r (dec c)]
     [(dec r) c]
     [r (inc c)]
     [(inc r) c]]))

(map #(vector % (get-in hm %)) (surround-coords [2 2]))

(remove nil?)

(defn elevate-from [hm level {:keys [visited unvisited] :as visit-map}]
  (if (seq unvisited)
    (elevate-from
      hm
      (inc level)
      {:visited   (reduce conj visited unvisited)
       :unvisited (vec (filter #(and
                                  (= (inc level) (get-in hm %))
                                  (not= 9 (get-in hm %))) (distinct (mapcat #(surround-coords %) unvisited))))})
    visit-map))

(elevate-from hm 5 {:visited [] :unvisited #{[2 2]}})


(range 2 1001 2)