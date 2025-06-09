(ns real-estate-fund-app.controller.asset
  (:require [clojure.java.jdbc :as jdbc]
            [real-estate-fund-app.logic.asset :as logic.asset]
            [real-estate-fund-app.adapter.quotation :as adapter.quotation]
            [schema.core :as s]))

(defn create-new-asset
  "Create a new asset in the database."
  [db table body]
  (let [quotation (adapter.quotation/return-value-quotation (:name_asset body))
        new-value-index-asset (logic.asset/return-index-asset quotation
                                                              (:value_avarage_price_asset body))
        ;TODO new-value-asset
        new-value-asset (:value_asset 100)
        ;TODO new-value0-difference-asset
        new-body-asset (assoc body
                         :quotation_asset quotation
                         :index_asset new-value-index-asset
                         :value_asset new-value-asset)]
    (jdbc/insert! db table new-body-asset)))