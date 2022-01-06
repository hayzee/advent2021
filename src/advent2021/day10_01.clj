(ns advent2021.day10-01)

(def FILE-NAME "resources/day10-input.txt")

(def FILE-NAME-TEST "resources/day10-input-test.txt")

(defn file-data
  ; Read file data and return a vector of strings for each row.
  [file-name]
  (clojure.string/split (slurp file-name) #"\r\n"))

(defn remove-brackets [bstr]
  (let [newbstr
        (-> bstr
            (clojure.string/replace "<>" "")
            (clojure.string/replace "()" "")
            (clojure.string/replace "[]" "")
            (clojure.string/replace "{}" ""))]
    (if (= bstr newbstr)
      bstr
      (remove-brackets newbstr))))

(defn invalid? [bstr]
  (some #{\) \> \] \}} bstr))

;(defn wellformed? [bstr]
;  (= "" (remove-brackets bstr)))

(defn syntax-error-score-char [c]
  (case c
    \) 3
    \] 57
    \} 1197
    \> 25137
    0))

(defn syntax-error-score [file-data]
  (->>
    file-data
    (map remove-brackets)
    (map invalid?)
    (map syntax-error-score-char)
    (reduce +)))

; 26397
(syntax-error-score (file-data FILE-NAME-TEST))

; 343863
(syntax-error-score (file-data FILE-NAME))
