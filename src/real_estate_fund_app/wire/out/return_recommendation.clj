(ns real-estate-fund-app.wire.out.return_recommendation
  (:require [schema.core :as s]))


(s/defschema recommendation-return-schema
  {:name-asset     s/Str
   :quantity-asset s/Num
   })