(ns real-estate-fund-app.unit.logic.asset-test
  (:require [clojure.test :refer :all]
            [real-estate-fund-app.logic.asset :refer [return-index-asset]]))


(deftest calculates-index-asset-with-valid-values
  (let [quotation 120
        average-price 100
        result (return-index-asset quotation average-price)]
    (is (= result 20N))))

(deftest returns-nil-when-quotation-is-missing
  (let [quotation nil
        average-price 100
        result (return-index-asset quotation average-price)]
    (is (nil? result))))

(deftest returns-nil-when-average-price-is-missing
  (let [quotation 120
        average-price nil
        result (return-index-asset quotation average-price)]
    (is (nil? result))))

(deftest calculates-index-asset-with-negative-values
  (let [quotation -50
        average-price 100
        result (return-index-asset quotation average-price)]
    (is (= result -150.0))))
; TODO revisar todas as classes para teste