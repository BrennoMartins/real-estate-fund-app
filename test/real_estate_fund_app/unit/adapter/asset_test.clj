(ns real-estate-fund-app.unit.adapter.asset-test
  (:require [clojure.test :refer :all]
            [real-estate-fund-app.adapter.asset :refer [wire-create-new-asset->internal-asset]]
            [real-estate-fund-app.wire.in.create-new-asset :as wire.in.create-new-asset]
            [real-estate-fund-app.model.asset :as model.asset]
            [schema.core :as s]))



(deftest creates-internal-asset-with-default-values
  (let [payload {:id_asset                  1
                 :quantity_asset            10
                 :value_average_price_asset 500
                 :name_asset                "Asset A"
                 :value_asset               1000}
        result (wire-create-new-asset->internal-asset payload)]
    (is (= (:quotation_asset result) 0))
    (is (= (:difference_asset result) 0))
    (is (= (:index_asset result) 0))
    (is (= (:name_asset result) "Asset A"))
    (is (= (:value_asset result) 1000)))
  )

(deftest handles-missing-required-fields
  (let [payload {:value_asset 1000}
        result (s/check model.asset/Asset-schema (wire-create-new-asset->internal-asset payload))]
    (is (map? result))
    (is (contains? result :name_asset))))

;TODO tenho que fazer um tratamento para nao receber campos extras no payload
;(deftest handles-extra-fields-in-payload
;  (let [payload {:name_asset "Asset B" :value_asset 2000 :extra_field "unexpected"}
;        result (wire-create-new-asset->internal-asset payload)]
;    (println payload)
;    (is (not (contains? result :extra_field)))
;    (is (= (:name_asset result) "Asset B"))
;    (is (= (:value_asset result) 2000))))