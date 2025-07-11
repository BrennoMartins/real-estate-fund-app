(ns real-estate-fund-app.model.recommendation
  (:require [schema.core :as s]))

(s/defschema Recommendation
  {:budget s/Num
   :update-asset s/Bool})
