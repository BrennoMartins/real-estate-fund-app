(ns real-estate-fund-app.controller.asset
  (:require [clojure.java.jdbc :as jdbc]
            [real-estate-fund-app.logic.asset :as logic.asset]
            [real-estate-fund-app.controller.quotation :as controller.quotation]
            [real-estate-fund-app.model.asset :as model.asset]
            [real-estate-fund-app.util.convert :as util.convert]
            [real-estate-fund-app.diplomatic.db.asset :as diplomatic.db.asset]
            [real-estate-fund-app.diplomatic.http-client :as diplomatic.http-client]
            [schema.core :as s])
  (:import (java.math RoundingMode)))


(s/defn return-all-assets :- model.asset/asset-schema
  "Return all assets"
  [db table]
  (let [all-asset (diplomatic.db.asset/return-all-assets-db db table)]
    (map util.convert/schema-keys-to-kebab-case all-asset)))

(defn create-new-asset
  "Create a new asset in the database."
  [db table body]
  (let [quotation (controller.quotation/return-value-quotation (:name-asset body)
                                                               (diplomatic.http-client/get-all-quotation-asset))
        sum-value-asset (logic.asset/return-sum-value-asset (return-all-assets db (name table)))
        sum-value-avg-asset (logic.asset/return-sum-value-average-price-asset (return-all-assets db (name table)))
        new-body-asset (logic.asset/return-calculated-values quotation body sum-value-asset sum-value-avg-asset)]
    (jdbc/insert! db table (util.convert/schema-keys-to-snake-case new-body-asset))
    ;TODO: APOS INSERIR ATUALIZAR AS % DE RECOMENDAÇÃO

    ))


(defn return-name
  [asset sum-value-asset sum-value-avg-asset]
  (let [total-value (+ (:value-asset asset) 10)]
    {:saida-valortotal total-value
     :teste-brenno     "brenno"
     }
    )
  )

(defn update-recommendation
  "Update the recommendation percentage for an asset."
  [db table]
  (let [list-asset (return-all-assets db (name table))
        sum-value-asset (logic.asset/return-sum-value-asset (return-all-assets db (name table)))
        sum-value-avg-asset (logic.asset/return-sum-value-average-price-asset (return-all-assets db (name table)))]
    (map (fn [asset]
           (let [percent-current (logic.asset/return-percent-current (:value-asset asset) sum-value-asset)
                 percent-difference-recommendation (logic.asset/return-perc-diff-recommendation (:percent-recommendation asset) percent-current)
                 quantity-fix (logic.asset/return-quantity-fix percent-difference-recommendation sum-value-avg-asset (:value-average-price-asset asset))
                 ]
              (-> asset
                  (assoc :percent-current percent-current)
                  (assoc :percent-difference-recommendation percent-difference-recommendation)
                  (assoc :quantity-fix quantity-fix))
              ;(jdbc/update! db table (util.convert/schema-keys-to-snake-case asset) ["id = ?" (:id asset)])

              ))
             list-asset)))

;TODO ajustar o que quebrei no insert



;TODO ajustar o update que ta zuado