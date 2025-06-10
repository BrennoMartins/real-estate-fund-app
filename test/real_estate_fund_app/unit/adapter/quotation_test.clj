(ns real-estate-fund-app.unit.adapter.quotation-test
  (:require [clojure.test :refer :all]
            [real-estate-fund-app.adapter.quotation :refer [return-value-quotation]]
            [real-estate-fund-app.diplomatic.http-client :as diplomatic.http-client])
  (:import (clojure.lang ExceptionInfo)))


(deftest returns-quotation-value-for-existing-asset
  (with-redefs [diplomatic.http-client/get-all-quotation-asset
                (fn [] [{:name "Asset A" :value 100} {:name "Asset B" :value 200}])]
    (let [result (return-value-quotation "Asset A")]
      (is (= result 100)))))

(deftest throws-error-when-asset-not-found
  (with-redefs [diplomatic.http-client/get-all-quotation-asset
                (fn [] [{:name "Asset A" :value 100} {:name "Asset B" :value 200}])]
    (is (thrown-with-msg? ExceptionInfo #"Quotation not found for asset!"
                          (return-value-quotation "Asset C")))))

(deftest handles-empty-quotation-list
  (with-redefs [diplomatic.http-client/get-all-quotation-asset
                (fn [] [])]
    (is (thrown-with-msg? ExceptionInfo #"Quotation not found for asset!"
                          (return-value-quotation "Asset A")))))
