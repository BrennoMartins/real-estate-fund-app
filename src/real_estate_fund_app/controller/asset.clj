(ns real-estate-fund-app.controller.asset
  (:require [clojure.java.jdbc :as jdbc]
            [real-estate-fund-app.logic.asset :as logic.asset]
            [schema.core :as s]))

(defn create-new-asset
  "docstring"
  [db table body]
  ;TODO erro quando estamos chamando o return-index-asset
  ; Error 500 java.lang.ClassCastException: class java.lang.Double cannot be cast to class clojure.lang.IFn
  (let [novo_valor (logic.asset/return-index-asset (:quotation_asset body) (:value_avarage_price_asset body))
        ]
    println(novo_valor)
  ;(jdbc/insert! db table body)
  )
  )