(ns real-estate-fund-app.controller.asset
  (:require [clojure.java.jdbc :as jdbc]
            [real-estate-fund-app.logic.asset :as logic.asset]
            [real-estate-fund-app.adapter.quotation :as adapter.quotation]
            [schema.core :as s]))

(defn create-new-asset
  "Create a new asset in the database."
  [db table body]
  (let [quotation (adapter.quotation/return-value-quotation (:name_asset body))
        new-body-asset (logic.asset/return-calculated-values quotation body)]
    (jdbc/insert! db table new-body-asset)))