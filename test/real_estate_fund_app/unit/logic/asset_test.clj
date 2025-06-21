(ns real-estate-fund-app.unit.logic.asset-test
  (:require [clojure.test :refer :all]
            [real-estate-fund-app.logic.asset :as logic.asset]
            [real-estate-fund-app.logic.asset :refer [return-index-asset]]))

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
