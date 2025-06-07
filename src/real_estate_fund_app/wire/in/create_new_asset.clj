(ns real-estate-fund-app.wire.in.create-new-asset
(:require [schema.core :as s]))

(s/defschema create-new-asset-schema
  {:id_asset                  (s/constrained s/Int (complement nil?) 'id-asset-required)
   :quantity_asset            s/Num
   :value_asset               (s/maybe s/Num)
   :value_avarage_price_asset (s/maybe s/Num)
   :name_asset                s/Str})
