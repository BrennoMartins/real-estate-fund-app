(ns real-estate-fund-app.adapter.asset
  (:require [real-estate-fund-app.wire.in.create-new-asset :as wire.in.create-new-asset]
            [real-estate-fund-app.model.asset :as model.asset]
            [schema.core :as s]))

(s/defn wire-create-new-asset->internal-asset :- model.asset/asset-schema
  [payload :- wire.in.create-new-asset/create-new-asset-schema]
   (->
     (assoc payload :quotation_asset 0)
     (assoc :value_total_average_price_asset 0)
     (assoc :percent_current 0)
     (assoc :percent_difference_recommendation 0)
     (assoc :quantity_fix 0)
     (assoc :profit_asset 0)
     (assoc :index_asset 0)
     (assoc :value_asset 0)
     ))