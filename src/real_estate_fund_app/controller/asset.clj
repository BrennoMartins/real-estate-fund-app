(ns real-estate-fund-app.controller.asset
  (:require [clojure.java.jdbc :as jdbc]
            [real-estate-fund-app.logic.asset :as logic.asset]
            [real-estate-fund-app.controller.quotation :as controller.quotation]
            [real-estate-fund-app.model.asset :as model.asset]
            [real-estate-fund-app.util.convert :as util.convert]
            [real-estate-fund-app.diplomatic.db.asset :as diplomatic.db.asset]
            [real-estate-fund-app.diplomatic.http-client :as diplomatic.http-client]
            [schema.core :as s]))


(s/defn return-all-assets :- model.asset/asset-schema
  "Return all assets"
  [db table]
  (let [all-asset (diplomatic.db.asset/return-all-assets-db db table)]
    (map util.convert/schema-keys-to-kebab-case all-asset)))
(defn create-new-asset
  "Create a new asset in the database."
  [db table body]
  (let [quotation (controller.quotation/return-value-quotation (:name-asset body )
                                                               (diplomatic.http-client/get-all-quotation-asset))
        ;TODO tentar converter o Key word para String
        sum-value-asset (logic.asset/return-sum-value-asset (return-all-assets db "real_estate_fund"))
        sum-value-avg-asset (logic.asset/return-sum-value-asset (return-all-assets db "real_estate_fund"))
        new-body-asset (logic.asset/return-calculated-values quotation body sum-value-asset sum-value-avg-asset)]

    (jdbc/insert! db table (util.convert/schema-keys-to-snake-case new-body-asset))))


