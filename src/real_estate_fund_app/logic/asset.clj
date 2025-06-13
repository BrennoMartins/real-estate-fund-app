(ns real-estate-fund-app.logic.asset
  (:require
    [real-estate-fund-app.model.asset :as model.asset]
    [schema.core :as s]))


;TODO FAZER TODOS OS TESTES UNITARIOS PARA O LOGIC ASSET
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
  (* 100 (/ value sum-value-asset)))

(defn return-quantity-fix
  [perc-diff-recommendation sum-value-avg-asset average-price]
  (let [total-all-asset-avg (* perc-diff-recommendation sum-value-avg-asset)]
    (/ total-all-asset-avg average-price)))


; TODO para o calculo do sum esta considerando so os que tao na base de dados e nao o novo q ta vindoo
(s/defn return-calculated-values :- model.asset/asset-schema
  "Calculate the values for the asset based on the quotation and average price."
  [quotation :- s/Num
   body :- model.asset/asset-schema
   sum-value-asset :- s/Num
   sum-value-avg-asset :- s/Num
   ]
  (let [average-price (:value-average-price-asset body)
        quantity (:quantity-asset body)
        index (return-index-asset quotation average-price)
        value (return-value-asset quantity quotation)
        value-total-avg (return-value-total-avg-asset quantity average-price)
        profit (return-profit-asset value value-total-avg)
        percent_current (return-percent-current value sum-value-asset)
        perc-diff-recommendation (return-perc-diff-recommendation (:percent-recommendation body) percent_current)
        quantity-fix (return-quantity-fix perc-diff-recommendation sum-value-avg-asset average-price)
        ]
    (-> body
        (assoc :quotation-asset quotation)
        (assoc :index-asset index)
        (assoc :value-total-average-price-asset value-total-avg)
        (assoc :profit-asset profit)
        (assoc :percent-current percent_current)
        (assoc :percent-difference-recommendation perc-diff-recommendation)
        (assoc :quantity-fix quantity-fix)
        (assoc :value-asset value))))


;TODO implementar o sum-value-asset
(defn return-sum-value-asset
  [assets]
  (reduce + (map :value-asset assets)))


;TODO implementar o sum-value-avg-asset