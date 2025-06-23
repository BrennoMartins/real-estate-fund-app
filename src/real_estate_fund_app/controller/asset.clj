(ns real-estate-fund-app.controller.asset
  (:require [clojure.java.jdbc :as jdbc]
            [real-estate-fund-app.logic.asset :as logic.asset]
            [real-estate-fund-app.controller.quotation :as controller.quotation]
            [real-estate-fund-app.model.asset :as model.asset]
            [real-estate-fund-app.util.convert :as util.convert]
            [real-estate-fund-app.diplomatic.db.asset :as db.asset]
            [real-estate-fund-app.diplomatic.http-client :as http-client]
            [schema.core :as s]))

;; -- Helpers ------------------------------------------------------------

(defn- calculate-recommendation-values
  "Calculate the recommendation values for a single asset."
  [asset sum-value sum-value-avg]
  (let [percent-current (logic.asset/return-percent-current (:value-asset asset) sum-value)
        percent-diff (logic.asset/return-perc-diff-recommendation (:percent-recommendation asset) percent-current)
        quantity-fix (logic.asset/return-quantity-fix percent-diff sum-value-avg (:value-average-price-asset asset))]
    (assoc asset
      :percent-current percent-current
      :percent-difference-recommendation percent-diff
      :quantity-fix quantity-fix)))

(defn- calculate-all-recommendations
  "Calculate recommendation values for all assets."
  [assets]
  (let [sum-value (logic.asset/return-sum-value-asset assets)
        sum-value-avg (logic.asset/return-sum-value-average-price-asset assets)]
    (mapv #(calculate-recommendation-values % sum-value sum-value-avg) assets)))

;; -- Controller functions ----------------------------------------------

(s/defn return-all-assets :- model.asset/asset-schema
  "Retorna todos os assets convertendo para kebab-case."
  [db table]
  (->> (db.asset/return-all-assets-db db table)
       (map util.convert/schema-keys-to-kebab-case)))

(defn update-values-asset
  "Update the values of all assets in the database for a given table."
  [db table]
  (let [assets (return-all-assets db (name table))
        updated-assets (calculate-all-recommendations assets)]
    (doseq [asset updated-assets]
      (jdbc/update! db
                    table
                    (util.convert/schema-keys-to-snake-case asset)
                    ["id_asset = ?" (:id-asset asset)]))
    updated-assets))

;TODO pensar em retirar a parte do db
(defn update-values-asset-recommendation
  "Update the values of all assets in the database for a given table."
  [new-list-assets]
  (let [updated-assets (calculate-all-recommendations new-list-assets)]
    updated-assets))

;TODO pensar em uma forma de receber uma lista de assets para criar
(defn recive-asset-list-to-create
  "Receive a list of assets to create in the database."
  [db table body]
  (let [assets (map #(util.convert/schema-keys-to-snake-case %) body)]
    (doseq [asset assets]
      (jdbc/insert! db table asset))
    (update-values-asset db table)))

(defn create-new-asset
  "Create a new asset in the database and update its values."
  [db table body]
  (let [quotation (controller.quotation/return-value-quotation
                    (:name-asset body)
                    (http-client/get-all-quotation-asset))
        new-asset (logic.asset/return-calculated-values quotation body)]
    (jdbc/insert! db table (util.convert/schema-keys-to-snake-case new-asset))
    (update-values-asset db table)))

(defn update-quotation-asset
  "Create a new asset in the database and update its values."
  [db table]
  (let [list-all-assets (return-all-assets db (name table))]
    (doseq [asset list-all-assets]
      (let [quotation (controller.quotation/return-value-quotation
                        (:name-asset asset)
                        (http-client/get-all-quotation-asset))
            updated-asset (logic.asset/return-calculated-values quotation asset)]
        (jdbc/update! db
                      table
                      (util.convert/schema-keys-to-snake-case updated-asset)
                      ["id_asset = ?" (:id-asset asset)])))
    (update-values-asset db table)
    )
   )


; ------- UPDATE ASSET ------------------------------------------------

           ;(jdbc/update! diplomatic.db.financialdb/db
           ;              :real_estate_fund
           ;              {:name_asset name-asset
           ;               :quantity_asset quantity-asset}
           ;              ["id_asset = ?" (Integer/parseInt id)])
