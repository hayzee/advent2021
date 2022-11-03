(ns advent2021.day11-01-test
  (:require [clojure.test :refer :all])
  (:require [advent2021.day11-01 :as sut]))

(def ^:private FILE-NAME "resources/day11-input.txt")

(def ^:private FILE-NAME-TEST "resources/day11-input-test.txt")

(deftest file-data-test
  (is (= ["11111" "19991" "19191" "19991" "11111"]
       (sut/file-data FILE-NAME-TEST))))

(deftest numberify-row-test
  (is (= [2 1 4 3 6 5 8 7 0 9] (sut/numberify-row "2143658709"))))

(def grid (sut/file-data->number-grid (sut/file-data FILE-NAME-TEST)))

(deftest file-data->number-grid-test
  (is (= [[1 1 1 1 1]
          [1 9 9 9 1]
          [1 9 1 9 1]
          [1 9 9 9 1]
          [1 1 1 1 1]]
         grid)))

(deftest inc-row-test
  (is (= [2 3 4 5 6 7 8 9 0 1] (sut/inc-row (sut/inc-row (range 10))))))

(deftest inc-grid-test
  (is (= [[2 2 2 2 2]
          [2 0 0 0 2]
          [2 0 2 0 2]
          [2 0 0 0 2]
          [2 2 2 2 2]] (sut/inc-grid grid))))

(deftest ncols-test
  (is (= 5 (sut/ncols grid))))

(deftest nrows-test
  (is (= 5 (sut/nrows grid))))

(deftest surrounding-coords-test
  (is (= [[0 1] [1 0] [1 1]] (sut/surrounding-coords [0 0] grid)))
  (is (=  [[0 0] [0 1] [0 2] [1 0] [1 2] [2 0] [2 1] [2 2]] (sut/surrounding-coords [1 1] grid)))
  (is (= [[1 1] [1 2] [1 3] [2 1] [2 3] [3 1] [3 2] [3 3]] (sut/surrounding-coords [2 2] grid)))
  (is (= [[2 2] [2 3] [2 4] [3 2] [3 4] [4 2] [4 3] [4 4]] (sut/surrounding-coords [3 3] grid)))
  (is (= [[3 3] [3 4] [3 5] [4 3] [4 5] [5 3] [5 4] [5 5]] (sut/surrounding-coords [4 4] grid)))
  (is (= [[4 4] [4 5] [5 4]] (sut/surrounding-coords [5 5] grid)))
  (is (= [[0 4] [1 4] [1 5]] (sut/surrounding-coords [0 5] grid)))
  (is (= [[4 0] [4 1] [5 1]] (sut/surrounding-coords [5 0] grid))))

(deftest inc-flasher-neighbour-test
  (is (= 2 (sut/inc-flasher-neighbour 1)))
  (is (= 0 (sut/inc-flasher-neighbour 0)))
  (is (= 0 (sut/inc-flasher-neighbour 9)))
  (is (= 9 (sut/inc-flasher-neighbour 8))))

(deftest update-coords-test
  (is (= [[0 1 1 1 1] [1 9 9 9 1] [1 9 1 9 1] [1 9 9 9 1] [1 1 1 1 1]]
         (sut/update-coords grid [[0 0] [0 0] [0 0] [0 0] [0 0] [0 0] [0 0] [0 0] [0 0] [0 0] [0 0] [0 0]] sut/inc-flasher-neighbour))))
