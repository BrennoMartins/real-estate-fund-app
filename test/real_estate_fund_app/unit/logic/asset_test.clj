(ns real-estate-fund-app.unit.logic.asset-test
  (:require [clojure.test :refer :all]
            [real-estate-fund-app.logic.asset :as logic.asset]))

(deftest return-quantity-fix-test
  "Test the return-quantity-fix function with various scenarios."
  (let [perc-diff-recommendation -0.4100M
        sum-value-avg-asset 27326.83M
        average-price 9.58M
        result (logic.asset/return-quantity-fix perc-diff-recommendation sum-value-avg-asset average-price)]
    (is (= -12M result))))

(deftest return-quantity-fix-test-rbr
  "Test the return-quantity-fix RBR"
  (let [perc-diff-recommendation 0.0200M
        sum-value-avg-asset 27326.83M
        average-price 86.00M
        result (logic.asset/return-quantity-fix perc-diff-recommendation sum-value-avg-asset average-price)]
    (is (= 0M result))))

(deftest return-quantity-fix-test-mcc
  "Test the return-quantity-fix MCC"
  (let [perc-diff-recommendation -0.2800M
        sum-value-avg-asset 27326.83M
        average-price 85.16M
        result (logic.asset/return-quantity-fix perc-diff-recommendation sum-value-avg-asset average-price)]
    (is (= -1M result))))


(deftest return-index-asset-test
  (is (= 50 (logic.asset/return-index-asset 150 100)))
  (is (= -50 (logic.asset/return-index-asset 50 100)))
  (is (= 0 (logic.asset/return-index-asset 100 100))))


(deftest return-value-total-avg-asset-valid-input
  (is (= 200 (logic.asset/return-value-total-avg-asset 10 20))))

(deftest return-value-total-avg-asset-zero-quantity
  (is (= 0 (logic.asset/return-value-total-avg-asset 0 20))))

(deftest return-value-total-avg-asset-zero-average-price
  (is (= 0 (logic.asset/return-value-total-avg-asset 10 0))))

(deftest return-value-asset-valid-input
  (is (= 300 (logic.asset/return-value-asset 15 20))))

(deftest return-value-asset-zero-quantity
  (is (= 0 (logic.asset/return-value-asset 0 20))))

(deftest return-value-asset-zero-quotation
  (is (= 0 (logic.asset/return-value-asset 15 0))))

(deftest return-profit-asset-positive-profit
  (is (= 50 (logic.asset/return-profit-asset 150 100))))

(deftest return-profit-asset-negative-profit
  (is (= -50 (logic.asset/return-profit-asset 100 150))))

(deftest return-profit-asset-zero-profit
  (is (= 0 (logic.asset/return-profit-asset 100 100))))

(deftest return-perc-diff-recommendation-valid-input
  (is (= 10 (logic.asset/return-perc-diff-recommendation 50 40))))

(deftest return-perc-diff-recommendation-negative-difference
  (is (= -10 (logic.asset/return-perc-diff-recommendation 40 50))))

(deftest return-percent-current-valid-input
  (is (= 25.0000M (logic.asset/return-percent-current 25 100))))

(deftest return-calculated-values-valid-input
  (let [body {:value-average-price-asset 50
              :quantity-asset 10}
        result (logic.asset/return-calculated-values 60 body)]
    (is (= 60 (:quotation-asset result)))
    (is (= 20 (:index-asset result)))
    (is (= 500 (:value-total-average-price-asset result)))
    (is (= 100 (:profit-asset result)))
    (is (= 600 (:value-asset result)))
    (is (= 0 (:percent-current result)))
    (is (= 0 (:percent-difference-recommendation result)))
    (is (= 0 (:quantity-fix result)))))

(deftest return-calculated-values-zero-quantity
  (let [body {:value-average-price-asset 50
              :quantity-asset 0}
        result (logic.asset/return-calculated-values 60 body)]
    (is (= 0 (:value-total-average-price-asset result)))
    (is (= 0 (:value-asset result)))
    (is (= 0 (:profit-asset result)))))

(deftest return-calculated-values-negative-quotation
  (let [body {:value-average-price-asset 50
              :quantity-asset 10}
        result (logic.asset/return-calculated-values -60 body)]
    (is (= -60 (:quotation-asset result)))
    (is (= -220 (:index-asset result)))
    (is (= -600 (:value-asset result)))
    (is (= -1100 (:profit-asset result)))))

(deftest return-sum-value-asset-valid-input
  (is (= 300 (logic.asset/return-sum-value-asset [{:value-asset 100} {:value-asset 200}]))))

(deftest return-sum-value-asset-empty-list
  (is (= 0 (logic.asset/return-sum-value-asset []))))

(deftest return-sum-value-average-price-asset-valid-input
  (is (= 500 (logic.asset/return-sum-value-average-price-asset [{:value-total-average-price-asset 200} {:value-total-average-price-asset 300}]))))

(deftest return-sum-value-average-price-asset-empty-list
  (is (= 0 (logic.asset/return-sum-value-average-price-asset []))))