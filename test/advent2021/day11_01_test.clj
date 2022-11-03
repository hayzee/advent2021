(ns advent2021.day11-01-test
  (:require [clojure.test :refer :all])
  (:require [advent2021.day11-01 :as sut]))

(def ^:private FILE-NAME "resources/day11-input.txt")

(def ^:private FILE-NAME-TEST "resources/day11-input-test.txt")

(deftest file-data-test
  (is (= ["11111" "19991" "19191" "19991" "11111"]
         (sut/file-data FILE-NAME-TEST))))

(def grid (sut/file-data->number-grid (sut/file-data FILE-NAME-TEST)))

(deftest file-data->number-grid-test
  (is (= [[1 1 1 1 1]
          [1 9 9 9 1]
          [1 9 1 9 1]
          [1 9 9 9 1]
          [1 1 1 1 1]]
         grid)))

(deftest ncols-test
  (is (= 5 (sut/ncols grid))))

(deftest nrows-test
  (is (= 5 (sut/nrows grid))))

(deftest surrounding-coords-test
  (is (= [[0 1] [1 0] [1 1]] (sut/surrounding-coords grid [0 0])))
  (is (= [[0 0] [0 1] [0 2] [1 0] [1 2] [2 0] [2 1] [2 2]] (sut/surrounding-coords grid [1 1])))
  (is (= [[1 1] [1 2] [1 3] [2 1] [2 3] [3 1] [3 2] [3 3]] (sut/surrounding-coords grid [2 2])))
  (is (= [[2 2] [2 3] [2 4] [3 2] [3 4] [4 2] [4 3] [4 4]] (sut/surrounding-coords grid [3 3])))
  (is (= [[3 3] [3 4] [4 3]] (sut/surrounding-coords grid [4 4])))
  (is (= [[0 3] [1 3] [1 4]] (sut/surrounding-coords grid [0 4])))
  (is (= [[3 0] [3 1] [4 1]] (sut/surrounding-coords grid [4 0]))))

(deftest find-in-grid-test
  (is (= [[0 0] [0 1] [0 2] [0 3] [0 4]
          [1 0] [1 1] [1 2] [1 3] [1 4]
          [2 0] [2 1] [2 2] [2 3] [2 4]
          [3 0] [3 1] [3 2] [3 3] [3 4]
          [4 0] [4 1] [4 2] [4 3] [4 4]]
         (sut/find-in-grid grid)))
  (is (= [[1 1] [1 2] [1 3]
          [2 1] [2 3]
          [3 1] [3 2] [3 3]]
         (sut/find-in-grid grid 9)))
  (is (= [[0 0] [0 1] [0 2] [0 3] [0 4]
          [1 0] [1 4]
          [2 0] [2 2] [2 4]
          [3 0] [3 4]
          [4 0] [4 1] [4 2] [4 3] [4 4]]
         (sut/find-in-grid grid 1))))

(deftest inc-board-square-test
  (is (= 1 (sut/inc-board-square 0)))
  (is (= 2 (sut/inc-board-square 1)))
  (is (= 9 (sut/inc-board-square 8)))
  (is (= 0 (sut/inc-board-square 9))))

(deftest inc-flasher-neighbour-test
  (is (= 0 (sut/inc-flasher-neighbour 0)))
  (is (= 2 (sut/inc-flasher-neighbour 1)))
  (is (= 9 (sut/inc-flasher-neighbour 8)))
  (is (= 0 (sut/inc-flasher-neighbour 9))))
