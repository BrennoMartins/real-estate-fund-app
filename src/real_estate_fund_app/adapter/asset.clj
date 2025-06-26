(ns real-estate-fund-app.adapter.asset
  (:require [real-estate-fund-app.wire.in.create-new-asset :as wire.in.create-new-asset]
            [real-estate-fund-app.wire.in.buy-asset :as wire.in.buy-asset]
            [real-estate-fund-app.model.asset :as model.asset]
            [schema.core :as s]))

(s/defn wire-create-new-asset->internal-asset :- model.asset/asset-schema
  [payload :- wire.in.create-new-asset/create-new-asset-schema]
   (->
     (assoc payload :quotation-asset 0)
     (assoc :value-total-average-price-asset 0)
     (assoc :percent-current 0)
     (assoc :percent-difference-recommendation 0)
     (assoc :quantity-fix 0)
     (assoc :profit-asset 0)
     (assoc :index-asset 0)
     (assoc :value-asset 0)
     ))


(s/defn wire-buy-asset->internal-asset :- model.asset/asset-schema
  [payload :- wire.in.buy-asset/buy-asset-schema]
  (->
    (assoc payload :quotation-asset 0)
    (assoc :value-total-average-price-asset 0)
    (assoc :percent-current 0)
    (assoc :percent-difference-recommendation 0)
    (assoc :quantity-fix 0)
    (assoc :profit-asset 0)
    (assoc :index-asset 0)
    (assoc :value-asset 0)
    (assoc :value-average-price-asset 0)
    (assoc :name-asset "")
    ))