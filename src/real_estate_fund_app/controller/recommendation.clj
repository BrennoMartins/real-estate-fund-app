(ns real-estate-fund-app.controller.recommendation
  (:require [real-estate-fund-app.model.recommendation :as model.recommendation]
            [real-estate-fund-app.model.asset :as model.asset]
            [real-estate-fund-app.wire.out.return_recommendation :as wire.out.return_recommendation]
            [real-estate-fund-app.controller.asset :as controller.asset]
            [schema.core :as s]))


(defn return-item-to-by
  [list-all-assets]
  (first (sort-by :quantity-fix #(compare %2 %1) list-all-assets))
  )

(s/defn return-options-buy ;:- wire.out.return_recommendation/recommendation-return-schema
  [db
   table
   recommendation-budget :- model.recommendation/Recommendation]
  (let [list-all-assets (controller.asset/return-all-assets db (name table))
        asset-to-buy (return-item-to-by list-all-assets)
        price-asset (* (:quantity-fix asset-to-buy) (:quotation-asset asset-to-buy))
        return-recommendation-teste {}]

    (if (> price-asset (:budget recommendation-budget))
      {:name-asset "nenhuma recomendação" :quantity-asset 0}

      (assoc return-recommendation-teste
        :name-asset (:name-asset asset-to-buy)
        :quantity-asset (:quantity-fix asset-to-buy)
        :total price-asset)
      ))
  )