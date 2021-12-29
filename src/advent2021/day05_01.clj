(ns advent2021.day05-01)

(def FILE-NAME-TEST "resources/day5-input-test.txt")

(def FILE-NAME "resources/day5-input.txt")

(defn file-data
  [file-name]
  (clojure.string/split (slurp file-name) #"\r\n"))

(defn line->line-segment
  [line]
  (let [[f s t] (clojure.string/split line #" ")]
    (read-string (apply str ["[[" f "]" "[" t "]]"]))))

(defn line-segments
  [file-data]
  (mapv line->line-segment file-data))

(defn range-inclusive
  "Generate a range from->to inclusive."
  [from to]
  (let [step (if (< to from) -1 1)
        to (if (neg? step) (dec to) (inc to))]
    (range from to step)))

(defn line-segment->segment-line-points
  "Generate a set of points for a given line-segment.
  For part 1, this only works for vertical and horizontal line segments."
  [[[x1 y1] [x2 y2]]]
  (cond
    (= x1 x2) (mapv vector (repeat x1) (range-inclusive y1 y2)) ; horizontal
    (= y1 y2) (mapv vector (range-inclusive x1 x2) (repeat y1)) ; vertical
    :else nil))

(defn line-segments->line-points
  [line-segments]
  (mapcat
    line-segment->segment-line-points
    line-segments))

(defn line-point-overlaps
  [line-points]
  (filter
    #(> (second %) 1)
    (frequencies line-points)))

(defn count-overlaps
  [file-data]
  (count
    (line-point-overlaps
      (line-segments->line-points
        (line-segments file-data)))))

(count-overlaps (file-data FILE-NAME-TEST))

(count-overlaps (file-data FILE-NAME))

