(ns real-estate-fund-app.diplomatic.db.financialdb
  (:require [clojure.java.jdbc :as jdbc]))

(def db
  {:dbtype "postgresql"
   :dbname "finance"
   :host "localhost"
   :port 5432
   :user "usuario"
   :password "1234"})

;(defn create-table []
;  (jdbc/execute! db
;                 ["CREATE TABLE IF NOT EXISTS public.real_estate_fund\n(\n
;                 difference_asset numeric(38,2),\n
;                 index_asset numeric(38,2),\n
;                 quantity_asset numeric(20,8) NOT NULL,\n
;                 quotation_asset numeric(38,2) NOT NULL,\n
;                 value_asset numeric(38,2),\n
;                 value_avarage_price_asset numeric(38,2),\n
;                 id_asset bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),\n
;                 name_asset character varying(255) COLLATE pg_catalog.\"default\" NOT NULL,\n
;                 CONSTRAINT real_estate_fund_pkey PRIMARY KEY (id_asset)\n)"]))
;




