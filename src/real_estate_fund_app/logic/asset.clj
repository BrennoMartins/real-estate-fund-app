(ns real-estate-fund-app.logic.asset
  (:require
    [real-estate-fund-app.model.asset :as model.asset]
    [schema.core :as s]))

(defn return-index-asset
  [quotation avarage-price]
  (let [percent (/ (- quotation avarage-price) avarage-price)]
    (* percent 100)))


(defn return-difference-asset
  [quotation avarage-price]
  (- quotation avarage-price))

(defn return-value-asset
  [quantity quotation]
  (* quantity quotation))


(s/defn return-calculated-values :- model.asset/asset-schema
  "Calculate the values for the asset based on the quotation and average price."
  [quotation :- s/Num
   body :- model.asset/asset-schema]
  (let [average-price (:value_average_price_asset body)
        quantity (:quantity_asset body)
        index (return-index-asset quotation average-price)
        value (return-value-asset quantity quotation)
        ;difference (return-difference-asset quotation average-price)
        ]
    (-> body
        (assoc :quotation_asset quotation)
        (assoc :index_asset index)
        ;(assoc :difference_asset difference)
        (assoc :value_asset value))))

;TODO implementar essas regras
;     (assoc :value_total_average_price_asset 0) (* quantity average-price)
;     (assoc :percent_current 0) -> (value_assert/sum(todos os valores) % teria que percorrer todos os ativos e pegar o valor total
;     (assoc :percent_difference_recommendation 0) (percent-recomendation - percent_current)
;     (assoc :quantity_fix 0)  (percent_difference_recommendation * soma de todos os ativos conm preciso medio) / preco medio
;     (assoc :profit_asset 0) ; (value_asset - value_total_average_price_asset)
