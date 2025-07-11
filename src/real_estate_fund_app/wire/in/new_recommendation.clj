(ns real-estate-fund-app.wire.in.new_recommendation
  (:require [schema.core :as s]))

(s/defschema new-recommendation-schema
  {:budget s/Num
   :update-asset s/Bool})