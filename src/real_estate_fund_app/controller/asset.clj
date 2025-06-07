(ns real-estate-fund-app.controller.asset
  (:require [clojure.java.jdbc :as jdbc]
            [schema.core :as s]))

(defn create-new-asset
  "docstring"
  [db table body]
  (jdbc/insert! db table body)
  )