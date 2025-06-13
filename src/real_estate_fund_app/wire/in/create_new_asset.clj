(ns real-estate-fund-app.wire.in.create-new-asset
(:require [schema.core :as s]))

(s/defschema create-new-asset-schema
  {:id-asset                  (s/constrained s/Int (complement nil?) 'id-asset-required)
   :quantity-asset            s/Num
   :percent-recommendation    s/Num
   :value-average-price-asset (s/maybe s/Num)
   :name-asset                s/Str})