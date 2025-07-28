(ns real-estate-fund-app.controller.asset
  (:require [clojure.java.jdbc :as jdbc]
            [real-estate-fund-app.controller.quotation :as controller.quotation]
            [real-estate-fund-app.diplomatic.db.asset :as db.asset]
            [real-estate-fund-app.logic.asset :as logic.asset]
            [real-estate-fund-app.model.asset :as model.asset]
            [real-estate-fund-app.util.convert :as util.convert]
            [real-estate-fund-app.util.import-json :as util.import-json]
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

(s/defn return-one-assets-by-id-db :- model.asset/asset-schema
  "Retorna todos os assets convertendo para kebab-case."
  [db table id]
  (->> (db.asset/return-one-assets-by-id-db db table id)
       (map util.convert/schema-keys-to-kebab-case)))

(defn update-values-asset-db
  "Update the values of all assets in the database for a given table."
  ([db table]
   (let [assets (return-all-assets db (name table))
         updated-assets (calculate-all-recommendations assets)]
     (update-values-asset-db db table updated-assets)))
  ;TODO testar esse novo padrao
  ([db table updated-assets]
   (doseq [asset updated-assets]
     (db.asset/update-asset-by-id db table (util.convert/schema-keys-to-snake-case asset)))
   updated-assets))

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
    ;TODO testar esse novo padrao
    (doseq [asset assets]
      (db.asset/insert-assert db table asset))
    (update-values-asset-db db table)))

(defn create-new-asset
  "Create a new asset in the database and update its values."
  [db table body]
  (let [quotation (controller.quotation/return-value-one-quotation (:name-asset body))
        new-asset (logic.asset/return-calculated-values quotation body)]
    ;TODO testar esse novo padrao
    (db.asset/insert-assert db table (util.convert/schema-keys-to-snake-case new-asset))
    (update-values-asset-db db table)))

(defn update-quotation-asset
  "Create a new asset in the database and update its values."
  [db table]
  (let [list-all-assets (return-all-assets db (name table))]
    (doseq [asset list-all-assets]
      (let [quotation (controller.quotation/return-value-one-quotation (:name-asset asset))
            updated-asset (logic.asset/return-calculated-values quotation asset)]
        ;TODO testar esse novo padrao
        (db.asset/update-asset-by-id db table (util.convert/schema-keys-to-snake-case updated-asset))))
    (update-values-asset-db db table)))

; TODO refatorar esse metodo
(defn buying-asset
  [db table body]
  (let [existing-asset (first (return-one-assets-by-id-db db table (:id-asset body)))
        quotation (controller.quotation/return-value-one-quotation (:name-asset existing-asset))]
    (println quotation)
    (if existing-asset
      (let [new-value_asset (+ (:value-asset existing-asset) (* (:quantity-asset body) quotation))
            new-value-average-price-asset (/ new-value_asset (+ (:quantity-asset body) (:quantity-asset existing-asset)))
            updated-asset (assoc existing-asset
                            :quantity-asset (+ (:quantity-asset existing-asset) (:quantity-asset body))
                            :value-average-price-asset new-value-average-price-asset)]
        ;TODO testar esse novo padrao
        (db.asset/update-asset-by-id db table (util.convert/schema-keys-to-snake-case updated-asset))
        (update-quotation-asset db table)))))

(defn reset-assets
  [db table]
  (let [asset-list (util.import-json/open-file "import.json")]
    ;TODO testar esse novo padrao
    (db.asset/delete-all db table)
    (doseq [asset asset-list]
      (create-new-asset db table asset))))