(ns real-estate-fund-app.wire.in.buy-asset
(:require [schema.core :as s]))

(s/defschema buy-asset-schema
  {:id-asset                  (s/constrained s/Int (complement nil?) 'id-asset-required)
   :quantity-asset            s/Num
   :percent-recommendation    s/Num})