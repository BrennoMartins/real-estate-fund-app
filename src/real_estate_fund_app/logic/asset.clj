(ns real-estate-fund-app.logic.asset
  (:require
    [real-estate-fund-app.model.asset :as model.asset]
    [schema.core :as s]))

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
  (* value sum-value-asset))

(defn return-quantity-fix
  [perc-diff-recommendation sum-value-avg-asset average-price]
  (let [total-all-asset-avg (* perc-diff-recommendation sum-value-avg-asset)]
    (/ total-all-asset-avg average-price)))

(s/defn return-calculated-values :- model.asset/asset-schema
  "Calculate the values for the asset based on the quotation and average price."
  [quotation :- s/Num
   body :- model.asset/asset-schema]
  (let [average-price (:value_average_price_asset body)
        quantity (:quantity_asset body)
        index (return-index-asset quotation average-price)
        value (return-value-asset quantity quotation)
        value-total-avg (return-value-total-avg-asset quantity average-price)
        profit (return-profit-asset value value-total-avg)
        ;TODO implementar o sum-value-asset
        sum-value-asset 0
        ;TODO implementar o sum-value-avg-asset
        sum-value-avg-asset 0
        percent_current (return-percent-current value sum-value-asset)
        perc-diff-recommendation (return-perc-diff-recommendation (:percent_recommendation body) percent_current)
        quantity-fix (return-quantity-fix perc-diff-recommendation sum-value-avg-asset average-price)
        ]
    (-> body
        (assoc :quotation_asset quotation)
        (assoc :index_asset index)
        (assoc :value_total_average_price_asset value-total-avg)
        (assoc :profit_asset profit)
        (assoc :percent_current percent_current)
        (assoc :percent_difference_recommendation perc-diff-recommendation)
        (assoc :quantity_fix quantity-fix)
        (assoc :value_asset value))))


;TODO refatorar todas as keywords para -