(ns real-estate-fund-app.controller.recommendation
  (:require [real-estate-fund-app.controller.asset :as controller.asset]
            [real-estate-fund-app.model.recommendation :as model.recommendation]
            [schema.core :as s]))

;TODO Refatorar todo esse código, está muito confuso e repetitivo
(defn return-item-to-buy
  [list-all-assets]
  (first (sort-by :quantity-fix #(compare %2 %1) list-all-assets))
  )

(defn return-updated-list-after-buy
  [list-all-assets
   asset-to-buy]
  (let [updated-assets (mapv (fn [asset]
                               (if (= (:name-asset asset) (:name-asset asset-to-buy))
                                 (assoc asset
                                   :quantity-asset (+ (:quantity-asset asset) (:quantity-fix asset-to-buy))
                                   :value-asset    (* (:quotation-asset asset) (+ (:quantity-asset asset) (:quantity-fix asset-to-buy)))
                                   :value-total-average-price-asset (* (:value-average-price-asset asset) (+ (:quantity-asset asset) (:quantity-fix asset-to-buy))))
                                 asset))
                             list-all-assets)]
    (controller.asset/update-values-asset-recommendation updated-assets)
  ))

 (s/defn return-options-buy
  [db
   table
   recommendation-budget :- model.recommendation/Recommendation]
  (let [initial-assets (controller.asset/return-all-assets db (name table))]
    (loop [remaining-assets initial-assets
           budget (:budget recommendation-budget)
           result []]
      (let [asset-to-buy (return-item-to-buy remaining-assets)
            price-asset (* (:quantity-fix asset-to-buy) (:quotation-asset asset-to-buy))]
        (if (or (nil? asset-to-buy) (> price-asset budget) (= price-asset 0.00M))
          (if (empty? result)
            {:name-asset "No recommendation found" :quantity-asset 0}
            result)
          (let [updated-assets (return-updated-list-after-buy remaining-assets asset-to-buy)
                new-budget (- budget price-asset)
                new-result (conj result {:name-asset (:name-asset asset-to-buy)
                                         :quantity-asset (:quantity-fix asset-to-buy)
                                         :total price-asset})]
            (controller.asset/update-values-asset-db db table updated-assets)
            (recur updated-assets new-budget new-result)))))))


(s/defn group-return-option-buy
  [db
   table
   recommendation-budget :- model.recommendation/Recommendation]
  (let [list-all-assets (return-options-buy db table recommendation-budget)]
    (->> list-all-assets
         (group-by :name-asset)
         (map (fn [[name entries]]
                {:name-asset     name
                 :quantity-asset (reduce + (map :quantity-asset entries))
                 :total          (reduce + (map :total entries))
                 }
                )
              )
         )
    )
  )
