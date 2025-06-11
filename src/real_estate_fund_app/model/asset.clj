(ns real-estate-fund-app.model.asset
  (:require [schema.core :as s]))

(s/defschema asset-schema
  {:id_asset                          (s/constrained s/Int (complement nil?) 'id-asset-required)
   :index_asset                       (s/maybe s/Num)
   :quantity_asset                    s/Num
   :quotation_asset                   s/Num
   :value_asset                       (s/maybe s/Num)
   :value_average_price_asset         (s/maybe s/Num)
   :value_total_average_price_asset   s/Num
   :percent_recommendation            s/Num
   :percent_current                   s/Num
   :percent_difference_recommendation s/Num
   :quantity_fix                      s/Int
   :profit_asset                      s/Num
   :name_asset                        s/Str})