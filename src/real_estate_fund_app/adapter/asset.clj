(ns real-estate-fund-app.adapter.asset
  (:require [real-estate-fund-app.wire.in.create-new-asset :as wire.in.create-new-asset]
            [real-estate-fund-app.model.asset :as model.asset]
            [schema.core :as s]))

(s/defn wire-create-new-asset->internal-asset :- model.asset/Asset-schema
  [payload :- wire.in.create-new-asset/create-new-asset-schema]
   (->
     (assoc payload :quotation_asset 120)
     (assoc :difference_asset 0)
     (assoc :index_asset 0)
     (assoc :value_asset 0)
     ))

