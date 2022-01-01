(ns advent2021.day08-01)

(def FILE-NAME-TEST "resources/day8-input-test.txt")

(def FILE-NAME "resources/day8-input.txt")

(defn file-data
  ; Read file data and return a vector of strings for each row.
  [file-name]
  (clojure.string/split (slurp file-name) #"\r\n"))

(defn file-data-row->signal-entry
  ; Take a single row of file-data and create a single signal-entry.
  [file-data-row]
  (->>
    (clojure.string/split file-data-row #"\|")
    (mapv clojure.string/trim)))

(defn signal-entries
  ; Create signal-entries [a vector of signal-entry] from file data.
  [file-data]
  (mapv file-data-row->signal-entry file-data))

(def seglen->digit
  {2 1
   4 4
   3 7
   7 8
   1 \|
   })

;; Part 1

(defn signal-output-tokens
  ; Work out digits for a signal entry
  [[signal-input signal-output]]
  (map #(seglen->digit (count %)) (clojure.string/split signal-output #" ")))

;; Check with test file

(->>
  (signal-entries (file-data FILE-NAME-TEST))
  (mapcat signal-output-tokens)
  (remove nil?)
  count)


;; Actual file

(->>
  (signal-entries (file-data FILE-NAME))
  (mapcat signal-output-tokens)
  (remove nil?)
  count)

