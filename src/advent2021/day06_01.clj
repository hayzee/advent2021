(ns advent2021.day06-01)

(def test-fish-data [3 4 3 1 2])

(def fish-data [1 3 1 5 5 1 1 1 5 1 1 1 3 1 1 4 3 1 1 2 2 4 2 1 3 3 2 4 4 4 1 3 1 1 4 3 1 5 5 1 1 3 4 2 1 5 3 4 5 5 2 5 5 1 5 5 2 1 5 1 1 2 1 1 1 4 4 1 3 3 1 5 4 4 3 4 3 3 1 1 3 4 1 5 5 2 5 2 2 4 1 2 5 2 1 2 5 4 1 1 1 1 1 4 1 1 3 1 5 2 5 1 3 1 5 3 3 2 2 1 5 1 1 1 2 1 1 2 1 1 2 1 5 3 5 2 5 2 2 2 1 1 1 5 5 2 2 1 1 3 4 1 1 3 1 3 5 1 4 1 4 1 3 1 4 1 1 1 1 2 1 4 5 4 5 5 2 1 3 1 4 2 5 1 1 3 5 2 1 2 2 5 1 2 2 4 5 2 1 1 1 1 2 2 3 1 5 5 5 3 2 4 2 4 1 5 3 1 4 4 2 4 2 2 4 4 4 4 1 3 4 3 2 1 3 5 3 1 5 5 4 1 5 1 2 4 2 5 4 1 3 3 1 4 1 3 3 3 1 3 1 1 1 1 4 1 2 3 1 3 3 5 2 3 1 1 1 5 5 4 1 2 3 1 3 1 1 4 1 3 2 2 1 1 1 3 4 3 1 3])

(defn fish-data->fish-state
  ; state is a vector of fish counts (of length 9) for fish status 0 -> 8 inclusive.
  [fish-data]
  (->>
   (merge
    (into {} (map vector (range 9) (repeat 0)))
    (frequencies fish-data))
   sort
   (mapv second)))

; test
(fish-data->fish-state test-fish-data)
(fish-data->fish-state fish-data)

(defn rotlv
  ; Utility function.
  ; Rotate a vector left by 1.
  [v]
  (let [f (first v)
        r (subvec v 1)]
    (conj r f)))

(defn generate-fish
  [fish-data]
  (let [togen (peek fish-data)]
    (update fish-data 6 #(+ togen %))))

(defn advance-state [fish-data]
  (generate-fish (rotlv fish-data)))

;; Part 1 test answer
(->> (iterate advance-state (fish-data->fish-state test-fish-data))
     (take 81)
     last
     (reduce +))

;; Part 1 actual answer
(->> (iterate advance-state (fish-data->fish-state fish-data))
     (take 81)
     last
     (reduce +))

;; Part 2 test answer
(->> (iterate advance-state (fish-data->fish-state test-fish-data))
     (take 257)
     last
     (reduce +))

;; Part 2 actual answer
(->> (iterate advance-state (fish-data->fish-state fish-data))
     (take 257)
     last
     (reduce +))

(comment

  ;; defunct - this is a bad idea !!!

  (defn dec-rotate [n]
    (let [dv (dec n)]
      (if (neg? dv)
        6
        dv)))

  (defn lantern-life [state]
    (let [state-dec (mapv dec-rotate state)
          new-gen (repeat (count (filter zero? state)) 8)]
      (apply conj state-dec new-gen)))

  (defn generate-lantern-fish [state days]
    (take (inc days) (iterate lantern-life state))))