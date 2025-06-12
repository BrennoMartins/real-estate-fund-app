(ns real-estate-fund-app.diplomatic.db.asset
  (:require [clojure.java.jdbc :as jdbc]
            [real-estate-fund-app.util.convert :as util.convert]
            [real-estate-fund-app.diplomatic.db.financialdb :as diplomatic.db.financialdb]
            [real-estate-fund-app.util.convert :as util.convert]))

; TODO fiz o metodo, mas agora eu tenho que fazer a implementacao dele no controller
(defn return-all-assets
  "Return all asset."
  [db table]
  (jdbc/query db
              [(str "SELECT * FROM " table " ORDER BY id_asset DESC")]))


; TODO Lembrar que tenho que converter o retorno para o formato do schema -
(return-all-assets diplomatic.db.financialdb/db "real_estate_fund")