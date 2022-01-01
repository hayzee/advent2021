(ns advent2021.day08-02)

(def FILE-NAME-TEST "resources/day8-input-test.txt")

(def FILE-NAME "resources/day8-input.txt")

(defn file-data
  ; Read file data and return a vector of strings for each row.
  [file-name]
  (clojure.string/split (slurp file-name) #"\r\n"))

(defn str->wordvec
  [instr]
  (clojure.string/split instr #" "))

(defn file-data-row->signal-entry
  ; Take a single row of file-data and create a single signal-entry.
  [file-data-row]
  (->>
    (clojure.string/split file-data-row #"\|")
    (mapv clojure.string/trim)
    (mapv str->wordvec)))

; num     #segs   unique              check order
; ======= ======= ==============			===========
; 0       6       subsumes 1 and
;                 NOT subsumes 4      2
; 1       2       Y                   1
; 2       5       minus 4 = #2set     4
; 3       5       subsumes 7          3
; 4       4       Y                   1
; 5       5       minus 4 = #1set     4
; 6       6       NOT subsumes 1      2
; 7       3       Y                   1
; 8       7       Y                   1
; 9       6       subsumes 1 and
;                 subsumes 4          2

(defn diffsize [s1 s2]
  "True if s2 is subsumed by s1"
  (count (clojure.set/difference s2 s1)))

(defn subsumes?
  "True if s2 is subsumed by s1"
  [s1 s2]
  (= 0 (diffsize s1 s2)))

(defn signal-input->digit-map
  ; Infer digit mappings for a given signal-input
  [signal-input]
  (->>
    (sort-by count signal-input)
    (reduce
      (fn [a e]
        (let [e (set e)]
          (cond
            (= 2 (count e)) (conj a [\1 e])
            (= 3 (count e)) (conj a [\7 e])
            (= 4 (count e)) (conj a [\4 e])
            (and (= 5 (count e)) (subsumes? (set e) (set (a \7)))) (conj a [\3 e])
            (and (= 5 (count e)) (= 2 (diffsize (set e) (set (a \4))))) (conj a [\2 e])
            (and (= 5 (count e)) (= 1 (diffsize (set e) (set (a \4))))) (conj a [\5 e])
            (and (= 6 (count e)) (not (subsumes? (set e) (set (a \1))))) (conj a [\6 e])
            (and (= 6 (count e)) (subsumes? (set e) (set (a \4)))) (conj a [\9 e])
            (and (= 6 (count e)) (not (subsumes? (set e) (set (a \4))))) (conj a [\0 e])
            (= 7 (count e)) (conj a [\8 e])
            :else (conj a [e "??"])))) {})
    clojure.set/map-invert))


(defn decode-signal-ouput
  [[signal-input signal-output]]
  (let [digit-map (signal-input->digit-map signal-input)]
    (->> signal-output
         (map set)
         (map digit-map))))


;; Test answer !!!
(->>
  (file-data FILE-NAME-TEST)
  (map file-data-row->signal-entry)
  (map decode-signal-ouput)
  (map #(apply str %))
  (map #(Integer/parseInt %))
  (reduce +))

;; The answer !!!
(->>
  (file-data FILE-NAME)
  (map file-data-row->signal-entry)
  (map decode-signal-ouput)
  (map #(apply str %))
  (map #(Integer/parseInt %))
  (reduce +))

