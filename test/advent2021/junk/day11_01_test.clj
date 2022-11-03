(ns advent2021.junk.day11-01-test
  (:require [clojure.test :refer :all])
  (:require [advent2021.day11-01 :refer :all])
  (:require [advent2021.util :refer :all]))

(def simple-grid-0
  [[1 1 1 1 1]
   [1 9 9 9 1]
   [1 9 1 9 1]
   [1 9 9 9 1]
   [1 1 1 1 1]])

(def simple-grid-1
  [[1 1 1 1]
   [1 9 9 1]
   [1 1 1 1]])

(def simple-grid-f
  [[2 2 2 2 2]
   [2 10 10 10 2]
   [2 10 2 10 2]
   [2 10 10 10 2]
   [2 2 2 2 2]])

(deftest row-count-test
  (is (= (row-count simple-grid-0)
         5)))

(deftest col-count-test
  (is (= (col-count simple-grid-1)
         4)))

(deftest all-coords-test
  (is (= (all-coords simple-grid-1)
         [[0 0] [0 1] [0 2] [0 3] [1 0] [1 1] [1 2] [1 3] [2 0] [2 1] [2 2] [2 3]])))

(deftest inc-grid-test
  (is (= (inc-grid simple-grid-1)
         [[2 2 2 2]
          [2 10 10 2]
          [2 2 2 2]])))

(deftest find-grid-test
  (is (= (find-grid simple-grid-1 #(= 9 %))
         [[1 1] [1 2]])))

(deftest surrounds-coords-test
  (is (= (surrounds-coords simple-grid-0 [0 0])
         [[0 1] [1 0] [1 1]]))
  (is (= (surrounds-coords simple-grid-0 [1 1])
         [[0 0] [0 1] [0 2] [1 0] [1 2] [2 0] [2 1] [2 2]]))
  (is (= (surrounds-coords simple-grid-0 [4 4])
         [[3 3] [3 4] [4 3]])))

(deftest flasher?-test
  (is (flasher? 10)))

(deftest find-flashers-test
  (is (= ((publicise find-flashers) simple-grid-f)
         [[1 1] [1 2] [1 3] [2 1] [2 3] [3 1] [3 2] [3 3]])))

(deftest get-flasher-adjacents-test
  ;  (is (= (get-flasher-adjacents simple-grid-f)))
  (is (= (get-flasher-adjacents [[10 1 1]
                                 [1 1 1]
                                 [1 1 1]])
         #{[0 1] [1 0] [1 1]}))
  (is (= (get-flasher-adjacents [[1 1 1]
                                 [1 1 1]
                                 [1 1 10]])
         #{[1 1] [1 2] [2 1]}))
  (is (= (get-flasher-adjacents [[10 1 1]
                                 [1 1 1]
                                 [1 1 10]])
         #{[0 1] [1 0] [1 1] [1 2] [2 1]}))
  (is (= (get-flasher-adjacents [[1 1 1]
                                 [1 10 1]
                                 [1 1 1]])
         #{[0 0] [0 1] [0 2] [1 0] [1 2] [2 0] [2 1] [2 2]}))
  (is (= (get-flasher-adjacents [[10 1 1]
                                 [1 1 1]
                                 [1 1 10]])
         #{[0 1] [1 0] [1 1] [1 2] [2 1]}))
  (is (= (get-flasher-adjacents [[10 10 1]
                                 [1 1 1]
                                 [1 1 1]])
         #{[0 2] [1 0] [1 1] [1 2]}))
  (is (= (get-flasher-adjacents [[10 10 1]
                                 [1 1 1]
                                 [0 0 0]])
         #{[0 2] [1 0] [1 1] [1 2]}))
  (is (empty? (get-flasher-adjacents [[0 0 1]
                                      [1 1 1]
                                      [0 0 0]])))
  )

(grid-step simple-grid-0)

;(comment
;
;  (def t-file-data
;    ["5483143223"
;     "2745854711"
;     "5264556173"
;     "6141336146"
;     "6357385478"
;     "4167524645"
;     "2176841721"
;     "6882881134"
;     "4846848554"
;     "5283751526"])
;
;  (deftest file-data-test
;    (is (= (file-data FILE-NAME-TEST)
;           t-file-data)))
;
;
;
;  (deftest file-data-row->grid-row-test
;    (is (= ((publicise file-data-row->grid-row) "12345")
;           [1 2 3 4 5])))
;
;  (deftest file->grid-test
;    (is (= (file->grid t-file-data)
;           t-grid)))
;
;  (deftest inc-octopus-test
;    (is (= (inc-octopus 0) 1))
;    (is (= (inc-octopus 1) 2))
;    (is (= (inc-octopus 8) 9))
;    (is (= (inc-octopus 9) 0)))
;
;  (deftest inc-grid-test
;    (is (= (inc-grid t-grid)
;           t-grid-next))
;    (is (= (inc-grid (inc-grid t-grid))
;           t-grid-next-next)))
;
;  (deftest find-zeros-test
;    (is (= (find-zeros t-grid-next-next))
;        [[0 2] [1 4] [4 5] [4 9] [6 4] [7 1] [7 2] [7 4] [7 5] [8 1] [8 4] [8 6] [9 2]]))
;
;  (deftest surrounds-coords-test
;    (is (= ((publicise surrounds-coords) [0 0])
;           [[0 1] [1 0] [1 1]]))
;    (is (= ((publicise surrounds-coords) [4 4])
;           [[3 3] [3 4] [3 5] [4 3] [4 5] [5 3] [5 4] [5 5]]))
;    (is (= ((publicise surrounds-coords) [10 10])
;           [[9 9]])))
;
;  (deftest surrounds-map-test
;    (is (= ((publicise surrounds-map) t-grid [0 0])
;           [{:coord [0 1], :value 4}
;            {:coord [1 0], :value 2}
;            {:coord [1 1], :value 7}]))
;    (is (= ((publicise surrounds-map) t-grid [4 4])
;           [{:coord [3 3], :value 1}
;            {:coord [3 4], :value 3}
;            {:coord [3 5], :value 3}
;            {:coord [4 3], :value 7}
;            {:coord [4 5], :value 8}
;            {:coord [5 3], :value 7}
;            {:coord [5 4], :value 5}
;            {:coord [5 5], :value 2}]))
;    (is (= ((publicise surrounds-map) t-grid [10 10])
;           [{:coord [9 9], :value 6}])))
;
;  (def simple-grid-0
;    [[1 1 1 1 1]
;     [1 9 9 9 1]
;     [1 9 1 9 1]
;     [1 9 9 9 1]
;     [1 1 1 1 1]])
;
;  (def simple-grid-1
;    [[3 4 5 4 3]
;     [4 0 0 0 4]
;     [5 0 0 0 5]
;     [4 0 0 0 4]
;     [3 4 5 4 3]])
;
;  (def simple-grid-2
;    [[4 5 6 5 4]
;     [5 1 1 1 5]
;     [6 1 1 1 6]
;     [5 1 1 1 5]
;     [4 5 6 5 4]]))
;
;
