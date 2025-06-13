(ns real-estate-fund-app.controller.asset
  (:require [clojure.java.jdbc :as jdbc]
            [real-estate-fund-app.logic.asset :as logic.asset]
            [real-estate-fund-app.controller.quotation :as controller.quotation]
            [real-estate-fund-app.util.convert :as util.convert]
            [real-estate-fund-app.diplomatic.db.asset :as diplomatic.db.asset]
            [real-estate-fund-app.diplomatic.http-client :as diplomatic.http-client]
            [schema.core :as s]))

(defn create-new-asset
  "Create a new asset in the database."
  [db table body]
  (let [quotation (controller.quotation/return-value-quotation (:name-asset body )
                                                               (diplomatic.http-client/get-all-quotation-asset))
        new-body-asset (logic.asset/return-calculated-values quotation body)]

    (jdbc/insert! db table (util.convert/schema-keys-to-snake-case new-body-asset))))

; TODO colocar a saida do metodo no formato do schema
(defn return-all-assets
  "Return all assets"
  [db table]
  (diplomatic.db.asset/return-all-assets-db db table))