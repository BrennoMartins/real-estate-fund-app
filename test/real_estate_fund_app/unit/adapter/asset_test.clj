(ns real-estate-fund-app.unit.adapter.asset-test
  (:require [clojure.test :refer :all]
            [real-estate-fund-app.adapter.asset :as adapter.asset]))


(deftest wire-create-new-asset-initializes-default-values
  (let [payload {:name-asset "Asset A", :quantity-asset 10}
        result (adapter.asset/wire-create-new-asset->internal-asset payload)]
    (is (= "Asset A" (:name-asset result)))
    (is (= 10 (:quantity-asset result)))
    (is (= 0 (:quotation-asset result)))
    (is (= 0 (:value-total-average-price-asset result)))
    (is (= 0 (:percent-current result)))
    (is (= 0 (:percent-difference-recommendation result)))
    (is (= 0 (:quantity-fix result)))
    (is (= 0 (:profit-asset result)))
    (is (= 0 (:index-asset result)))
    (is (= 0 (:value-asset result)))))

(deftest wire-buy-asset-initializes-default-values
  (let [payload {:name-asset "Asset B", :quantity-asset 5}
        result (adapter.asset/wire-buy-asset->internal-asset payload)]
    (is (= "" (:name-asset result)))
    (is (= 5 (:quantity-asset result)))
    (is (= 0 (:quotation-asset result)))
    (is (= 0 (:value-total-average-price-asset result)))
    (is (= 0 (:percent-current result)))
    (is (= 0 (:percent-difference-recommendation result)))
    (is (= 0 (:quantity-fix result)))
    (is (= 0 (:profit-asset result)))
    (is (= 0 (:index-asset result)))
    (is (= 0 (:value-asset result)))
    (is (= 0 (:value-average-price-asset result)))))