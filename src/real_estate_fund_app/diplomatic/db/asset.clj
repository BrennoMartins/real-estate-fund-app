(ns real-estate-fund-app.diplomatic.db.asset
  (:require [clojure.java.jdbc :as jdbc]
            [real-estate-fund-app.model.asset :as model.asset]
            [schema.core :as s]
            [real-estate-fund-app.diplomatic.db.financialdb :as diplomatic.db.financialdb]))

; TODO colocar a saida do metodo no formato do schema
(s/defn return-all-assets-db :- model.asset/asset-schema
  "Return all asset."
  [db table]
  (jdbc/query db
              [(str "SELECT * FROM " table " ORDER BY id_asset DESC")]))


; TODO Lembrar que tenho que converter o retorno para o formato do schema -
(return-all-assets-db diplomatic.db.financialdb/db "real_estate_fund")