(ns real-estate-fund-app.controller.recommendation
  (:require [real-estate-fund-app.model.recommendation :as model.recommendation]
            [real-estate-fund-app.wire.out.return_recommendation :as wire.out.return_recommendation]
            [schema.core :as s]))

(s/defn return-options-buy :- wire.out.return_recommendation/recommendation-return-schema
  [db
   table
   recommendation-budget :- model.recommendation/Recommendation]
  {:name-asset "Compra esse meu parca"
   :quantity-asset 10 }
  )
