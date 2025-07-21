(ns real-estate-fund-app.diplomatic.db.asset
  (:require [clojure.java.jdbc :as jdbc]
            [real-estate-fund-app.model.asset :as model.asset]
            [schema.core :as s]))

(s/defn return-all-assets-db :- model.asset/asset-schema
  "Return all assets."
  [db table]
  (jdbc/query db
              [(str "SELECT * FROM " table " ORDER BY id_asset DESC")]))

(s/defn return-one-assets-by-id-db :- model.asset/asset-schema
  "Return one asset."
  [db table id]
  (println id)
  (jdbc/query db
              [(str "SELECT * FROM " table " WHERE id_asset = ?") id]))