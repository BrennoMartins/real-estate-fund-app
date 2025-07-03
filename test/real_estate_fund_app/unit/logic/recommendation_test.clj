(ns real-estate-fund-app.unit.logic.recommendation-test
  (:require [clojure.test :refer :all]
            [schema.core :as s]
            [real-estate-fund-app.model.asset :as model.asset]
            [real-estate-fund-app.logic.recommendation :as logic.recommendation]))

(deftest return-item-to-buy-valid-input
  (is (= {:name-asset "Asset A", :quantity-fix 5}
         (logic.recommendation/return-item-to-buy [{:name-asset "Asset A", :quantity-fix 5}
                              {:name-asset "Asset B", :quantity-fix 3}]))))

(deftest return-item-to-buy-empty-list
  (is (nil? (logic.recommendation/return-item-to-buy []))))

; TODO Corrigir esse teste, pois o resultado esperado não é o correto
 (def sample-assets
  [(s/validate model.asset/asset-schema
     {:id-asset 1
      :index-asset 100.0
      :quantity-asset 10
      :quotation-asset 50.0
      :value-asset 500.0
      :value-average-price-asset 50.0
      :value-total-average-price-asset 500.0
      :percent-recommendation 20.0
      :percent-current 18.0
      :percent-difference-recommendation 2.0
      :quantity-fix 0
      :profit-asset 50.0
      :name-asset "Asset A"})
   (s/validate model.asset/asset-schema
     {:id-asset 2
      :index-asset 200.0
      :quantity-asset 5
      :quotation-asset 100.0
      :value-asset 500.0
      :value-average-price-asset 100.0
      :value-total-average-price-asset 500.0
      :percent-recommendation 30.0
      :percent-current 28.0
      :percent-difference-recommendation 2.0
      :quantity-fix 1
      :profit-asset 25.0
      :name-asset "Asset B"})])

(def sample-assets-buy
  [(s/validate model.asset/asset-schema
               {:id-asset 1
                :index-asset 100.0
                :quantity-asset 10
                :quotation-asset 50.0
                :value-asset 500.0
                :value-average-price-asset 50.0
                :value-total-average-price-asset 500.0
                :percent-recommendation 20.0
                :percent-current 18.0
                :percent-difference-recommendation 2.0
                :quantity-fix 1
                :profit-asset 50.0
                :name-asset "Asset A"})])
;
(deftest return-list-after-buy-valid-input
  (is (= [{:name-asset "Asset A", :quantity-asset 15, :value-asset 1500, :value-total-average-price-asset 750, :quotation-asset 100 :value-average-price-asset 50}
          {:name-asset "Asset B", :quantity-asset 10, :value-asset 1000, :value-total-average-price-asset 500, :quotation-asset 100 :value-average-price-asset 50}]
         (logic.recommendation/return-list-after-buy sample-assets sample-assets-buy))))

(deftest return-list-after-buy-no-match
  (is (= [{:name-asset "Asset A", :quantity-asset 10, :quotation-asset 100, :value-average-price-asset 50}
          {:name-asset "Asset B", :quantity-asset 10, :quotation-asset 100, :value-average-price-asset 50}]
         (logic.recommendation/return-list-after-buy [{:name-asset "Asset A", :quantity-asset 10, :quotation-asset 100, :value-average-price-asset 50}
                                 {:name-asset "Asset B", :quantity-asset 10, :quotation-asset 100, :value-average-price-asset 50}]
                                {:name-asset "Asset C", :quantity-fix 5}))))

(deftest group-option-buy-valid-input
  (is (= [{:name-asset "Asset A", :quantity-asset 15, :total 300}
          {:name-asset "Asset B", :quantity-asset 10, :total 200}]
         (logic.recommendation/group-option-buy [{:name-asset "Asset A", :quantity-asset 10, :total 200}
                            {:name-asset "Asset A", :quantity-asset 5, :total 100}
                            {:name-asset "Asset B", :quantity-asset 10, :total 200}]))))

(deftest group-option-buy-empty-list
  (is (= [] (logic.recommendation/group-option-buy []))))

(deftest should-stop-buying-valid-input
  (is (false? (logic.recommendation/should-stop-buying? {:name-asset "Asset A", :quantity-fix 5, :quotation-asset 100} 600))))

(deftest should-stop-buying-budget-exceeded
  (is (true? (logic.recommendation/should-stop-buying? {:name-asset "Asset A", :quantity-fix 5, :quotation-asset 200} 600))))

(deftest should-stop-buying-zero-price
  (is (true? (logic.recommendation/should-stop-buying? {:name-asset "Asset A", :quantity-fix 0, :quotation-asset 100} 600))))

(deftest should-stop-buying-nil-asset
  (is (true? (logic.recommendation/should-stop-buying? nil 600))))

(deftest build-buy-result-valid-input
  (is (= {:name-asset "Asset A", :quantity-asset 5, :total 500}
         (logic.recommendation/build-buy-result {:name-asset "Asset A", :quantity-fix 5} 500))))