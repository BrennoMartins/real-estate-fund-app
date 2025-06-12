(ns real-estate-fund-app.model.asset
  (:require [schema.core :as s]))

(s/defschema asset-schema
  {:id-asset                          (s/constrained s/Int (complement nil?) 'id-asset-required)
   :index-asset                       (s/maybe s/Num)
   :quantity-asset                    s/Num
   :quotation-asset                   s/Num
   :value-asset                       (s/maybe s/Num)
   :value-average-price-asset         (s/maybe s/Num)
   :value-total-average-price-asset   s/Num
   :percent-recommendation            s/Num
   :percent-current                   s/Num
   :percent-difference-recommendation s/Num
   :quantity-fix                      s/Int
   :profit-asset                      s/Num
   :name-asset                        s/Str})