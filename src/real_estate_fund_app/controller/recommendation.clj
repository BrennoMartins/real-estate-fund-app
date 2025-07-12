(ns real-estate-fund-app.controller.recommendation
  (:require [real-estate-fund-app.controller.asset :as controller.asset]
            [real-estate-fund-app.model.recommendation :as model.recommendation]
            [real-estate-fund-app.logic.recommendation :as logic.recommendation]
            [schema.core :as s]))
;TODO criar uma flag para verificar se quero alterar a base de dados ou apenas retornar a lista de ativos recomendados
(defn return-updated-list-after-buy
  [list-all-assets
   asset-to-buy]
  (let [updated-assets (logic.recommendation/return-list-after-buy list-all-assets asset-to-buy)]
    (controller.asset/update-values-asset-recommendation updated-assets)))

(s/defn return-options-buy
  [db table recommendation-budget :- model.recommendation/Recommendation]
  (let [initial-assets (controller.asset/return-all-assets db (name table))
        initial-budget (:budget recommendation-budget)
        update-database? (= (:update-asset recommendation-budget) "true")]

    (loop [remaining-assets initial-assets
           budget initial-budget
           result []]

      (let [asset-to-buy (logic.recommendation/return-item-to-buy remaining-assets)
            price (logic.recommendation/calculate-price asset-to-buy)]

        (if (logic.recommendation/should-stop-buying? asset-to-buy budget)
          (if (empty? result)
            [{:name-asset "No recommendation found" :quantity-asset 0}]
            result)

          (let [updated-assets (return-updated-list-after-buy remaining-assets asset-to-buy)
                updated-budget (- budget price)
                updated-result (conj result (logic.recommendation/build-buy-result asset-to-buy price))]
            (when update-database?
              (controller.asset/update-values-asset-db db table updated-assets))
            (recur updated-assets updated-budget updated-result)))))))

(s/defn group-return-option-buy
  [db
   table
   recommendation-budget :- model.recommendation/Recommendation]
  (let [list-all-assets (return-options-buy db table recommendation-budget)]
    (logic.recommendation/group-option-buy list-all-assets)))