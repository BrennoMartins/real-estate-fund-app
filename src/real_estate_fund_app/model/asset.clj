(ns real-estate-fund-app.model.asset
  (:require [schema.core :as s]))

(s/defschema Asset-schema
  {:id_asset                  (s/constrained s/Int (complement nil?) 'id-asset-required)
   :difference_asset          (s/maybe s/Num)
   :index_asset               (s/maybe s/Num)
   :quantity_asset            s/Num
   :quotation_asset           s/Num
   :value_asset               (s/maybe s/Num)
   :value_avarage_price_asset (s/maybe s/Num)
   :name_asset                s/Str})


