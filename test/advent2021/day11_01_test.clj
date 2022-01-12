(ns advent2021.day11-01-test
  (:require [clojure.test :refer :all])
  (:require [advent2021.day11-01 :refer :all])
  (:require [advent2021.util :refer :all]))

(def t-file-data
  ["5483143223"
   "2745854711"
   "5264556173"
   "6141336146"
   "6357385478"
   "4167524645"
   "2176841721"
   "6882881134"
   "4846848554"
   "5283751526"])

(def t-grid
  [[5 4 8 3 1 4 3 2 2 3]
   [2 7 4 5 8 5 4 7 1 1]
   [5 2 6 4 5 5 6 1 7 3]
   [6 1 4 1 3 3 6 1 4 6]
   [6 3 5 7 3 8 5 4 7 8]
   [4 1 6 7 5 2 4 6 4 5]
   [2 1 7 6 8 4 1 7 2 1]
   [6 8 8 2 8 8 1 1 3 4]
   [4 8 4 6 8 4 8 5 5 4]
   [5 2 8 3 7 5 1 5 2 6]])

(def t-grid-next
  [[6 5 9 4 2 5 4 3 3 4]
   [3 8 5 6 9 6 5 8 2 2]
   [6 3 7 5 6 6 7 2 8 4]
   [7 2 5 2 4 4 7 2 5 7]
   [7 4 6 8 4 9 6 5 8 9]
   [5 2 7 8 6 3 5 7 5 6]
   [3 2 8 7 9 5 2 8 3 2]
   [7 9 9 3 9 9 2 2 4 5]
   [5 9 5 7 9 5 9 6 6 5]
   [6 3 9 4 8 6 2 6 3 7]])

(def t-grid-next-next
  [[7 6 0 5 3 6 5 4 4 5]
   [4 9 6 7 0 7 6 9 3 3]
   [7 4 8 6 7 7 8 3 9 5]
   [8 3 6 3 5 5 8 3 6 8]
   [8 5 7 9 5 0 7 6 9 0]
   [6 3 8 9 7 4 6 8 6 7]
   [4 3 9 8 0 6 3 9 4 3]
   [8 0 0 4 0 0 3 3 5 6]
   [6 0 6 8 0 6 0 7 7 6]
   [7 4 0 5 9 7 3 7 4 8]])

(deftest file-data-test
  (is (= (file-data FILE-NAME-TEST)
         t-file-data)))

(deftest file-data-row->grid-row-test
  (is (= ((publicise file-data-row->grid-row) "12345")
         [1 2 3 4 5])))

(deftest file->grid-test
  (is (= (file->grid t-file-data)
         t-grid)))

(deftest inc-octopus-test
  (is (= (inc-octopus 0) 1))
  (is (= (inc-octopus 1) 2))
  (is (= (inc-octopus 8) 9))
  (is (= (inc-octopus 9) 0)))

(deftest inc-grid-test
  (is (= (inc-grid t-grid)
         t-grid-next))
  (is (= (inc-grid (inc-grid t-grid))
         t-grid-next-next)))

(deftest find-zeros-test
  (is (= (find-zeros t-grid-next-next))
      [[0 2] [1 4] [4 5] [4 9] [6 4] [7 1] [7 2] [7 4] [7 5] [8 1] [8 4] [8 6] [9 2]]))

(deftest surrounds-coords-test
  (is (= ((publicise surrounds-coords) [0 0])
         [[0 1] [1 0] [1 1]]))
  (is (= ((publicise surrounds-coords) [4 4])
         [[3 3] [3 4] [3 5] [4 3] [4 5] [5 3] [5 4] [5 5]]))
  (is (= ((publicise surrounds-coords) [10 10])
         [[9 9]])))

(deftest surrounds-map-test
  (is (= ((publicise surrounds-map) t-grid [0 0])
         [{:coord [0 1], :value 4}
          {:coord [1 0], :value 2}
          {:coord [1 1], :value 7}]))
  (is (= ((publicise surrounds-map) t-grid [4 4])
         [{:coord [3 3], :value 1}
          {:coord [3 4], :value 3}
          {:coord [3 5], :value 3}
          {:coord [4 3], :value 7}
          {:coord [4 5], :value 8}
          {:coord [5 3], :value 7}
          {:coord [5 4], :value 5}
          {:coord [5 5], :value 2}]))
  (is (= ((publicise surrounds-map) t-grid [10 10])
         [{:coord [9 9], :value 6}])))

(def simple-grid-0
  [[1 1 1 1 1]
   [1 9 9 9 1]
   [1 9 1 9 1]
   [1 9 9 9 1]
   [1 1 1 1 1]])

(def simple-grid-1
  [[3 4 5 4 3]
   [4 0 0 0 4]
   [5 0 0 0 5]
   [4 0 0 0 4]
   [3 4 5 4 3]])

(def simple-grid-2
  [[4 5 6 5 4]
   [5 1 1 1 5]
   [6 1 1 1 6]
   [5 1 1 1 5]
   [4 5 6 5 4]])


