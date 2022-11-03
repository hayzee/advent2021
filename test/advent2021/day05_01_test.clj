(ns advent2021.day05-01-test
  (:require [clojure.test :refer :all])
  (:require [advent2021.day05-01 :refer :all]))

(def fdat ["0,9 -> 5,9"
           "8,0 -> 0,8"
           "9,4 -> 3,4"
           "2,2 -> 2,1"
           "7,0 -> 7,4"
           "6,4 -> 2,0"
           "0,9 -> 2,9"
           "3,4 -> 1,4"
           "0,0 -> 8,8"
           "5,5 -> 8,2"])

(def line-segments-dat
  [[[0 9] [5 9]]
   [[8 0] [0 8]]
   [[9 4] [3 4]]
   [[2 2] [2 1]]
   [[7 0] [7 4]]
   [[6 4] [2 0]]
   [[0 9] [2 9]]
   [[3 4] [1 4]]
   [[0 0] [8 8]]
   [[5 5] [8 2]]])

(def line-points-dat
  [[0 9]
   [1 9]
   [2 9]
   [3 9]
   [4 9]
   [5 9]
   [9 4]
   [8 4]
   [7 4]
   [6 4]
   [5 4]
   [4 4]
   [3 4]
   [2 2]
   [2 1]
   [7 0]
   [7 1]
   [7 2]
   [7 3]
   [7 4]
   [0 9]
   [1 9]
   [2 9]
   [3 4]
   [2 4]
   [1 4]])

(deftest file-data-test
  (is (= (file-data "resources/day5-input-test.txt")
         fdat)))

(deftest line->line-segment-test
  (is (= (line->line-segment "0,9 -> 5,9") [[0 9] [5 9]])))

(deftest line-segments-test
  (is (= (line-segments fdat)
         [[[0 9] [5 9]]
          [[8 0] [0 8]]
          [[9 4] [3 4]]
          [[2 2] [2 1]]
          [[7 0] [7 4]]
          [[6 4] [2 0]]
          [[0 9] [2 9]]
          [[3 4] [1 4]]
          [[0 0] [8 8]]
          [[5 5] [8 2]]])))

(deftest range-inclusive-test
  (is (= (range-inclusive 10 0) [10 9 8 7 6 5 4 3 2 1 0]))
  (is (= (range-inclusive 0 10) [0 1 2 3 4 5 6 7 8 9 10]))
  (is (= (range-inclusive -10 0) [-10 -9 -8 -7 -6 -5 -4 -3 -2 -1 0]))
  (is (= (range-inclusive 0 -10) [0 -1 -2 -3 -4 -5 -6 -7 -8 -9 -10]))
  (is (= (range-inclusive 5 10) [5 6 7 8 9 10]))
  (is (= (range-inclusive -5 -10) [-5 -6 -7 -8 -9 -10]))
  (is (= (range-inclusive 10 5) [10 9 8 7 6 5]))
  (is (= (range-inclusive -10 -5) [-10 -9 -8 -7 -6 -5]))
  (is (= (range-inclusive -5 5) [-5 -4 -3 -2 -1 0 1 2 3 4 5]))
  (is (= (range-inclusive 5 -5) [5 4 3 2 1 0 -1 -2 -3 -4 -5])))

(deftest line-segment->segment-line-points-test
  (is (= (line-segment->segment-line-points [[0 -2] [0 2]]) [[0 -2] [0 -1] [0 0] [0 1] [0 2]]))
  (is (= (line-segment->segment-line-points [[0 2] [0 -2]]) [[0 2] [0 1] [0 0] [0 -1] [0 -2]]))
  (is (= (line-segment->segment-line-points [[-2 0] [2 0]]) [[-2 0] [-1 0] [0 0] [1 0] [2 0]]))
  (is (= (line-segment->segment-line-points [[2 0] [-2 0]]) [[2 0] [1 0] [0 0] [-1 0] [-2 0]])))

(deftest line-segments->line-points-test
  (is (= (line-segments->line-points line-segments-dat) line-points-dat)))

(deftest line-point-overlaps-test
  (is (= (count (line-point-overlaps line-points-dat)) 5)))
