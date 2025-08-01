(ns real-estate-fund-app.logic.asset
  (:require
    [real-estate-fund-app.model.asset :as model.asset]
    [schema.core :as s])
  (:import (java.math RoundingMode)))

(defn return-index-asset
  [quotation average-price]
  (let [percent (/ (- quotation average-price) average-price)]
    (* percent 100)))

(defn return-value-total-avg-asset
  [quantity average-price]
  (* quantity average-price))

(defn return-value-asset
  [quantity quotation]
  (* quantity quotation))

(defn return-profit-asset
  [value value-total-avg]
  (- value value-total-avg))

(defn return-perc-diff-recommendation
  [percent-recommendation percent_current]
  ( - percent-recommendation percent_current))

(defn return-percent-current
  [value sum-value-asset]
  (-> (bigdec value)
      (.divide (bigdec sum-value-asset) 4 RoundingMode/HALF_UP)
      (.multiply (BigDecimal/valueOf 100))))

(defn return-quantity-fix
  [perc-diff-recommendation sum-value-avg-asset average-price]
  (let [total-all-asset-avg (.multiply perc-diff-recommendation sum-value-avg-asset)
        value-divide (.divide total-all-asset-avg average-price 4 RoundingMode/HALF_UP)
        result (.divide value-divide (bigdec 100) 0 RoundingMode/HALF_UP)]
    result))

(s/defn return-calculated-values :- model.asset/asset-schema
  "Calculate the values for the asset based on the quotation and average price."
  [quotation :- s/Num
   body :- model.asset/asset-schema
   ]
  (let [average-price (:value-average-price-asset body)
        quantity (:quantity-asset body)
        index (return-index-asset quotation average-price)
        value (return-value-asset quantity quotation)
        value-total-avg (return-value-total-avg-asset quantity average-price)
        profit (return-profit-asset value value-total-avg)
        ]
    (-> body
        (assoc :quotation-asset quotation)
        (assoc :index-asset index)
        (assoc :value-total-average-price-asset value-total-avg)
        (assoc :profit-asset profit)
        (assoc :percent-current 0)
        (assoc :percent-difference-recommendation 0)
        (assoc :quantity-fix 0)
        (assoc :value-asset value))))

(defn return-sum-value-asset
  [assets]
  (reduce + (map :value-asset assets)))

 (defn return-sum-value-average-price-asset
  [assets]
  (reduce + (map :value-total-average-price-asset assets)))