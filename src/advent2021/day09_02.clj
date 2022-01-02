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

(def hm-test (file-data->height-map (file-data FILE-NAME-TEST)))
(def hm (file-data->height-map (file-data FILE-NAME)))

(risk-level hm)

;2199943210
;3987894921
;9856789892
;8767896789
;9899965678


