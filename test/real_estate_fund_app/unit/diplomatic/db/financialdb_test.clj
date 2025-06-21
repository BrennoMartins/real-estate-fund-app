(ns real-estate-fund-app.unit.diplomatic.db.financialdb-test
  (:require [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]))


(def db
  {:dbtype "postgresql"
   :dbname "finance"
   :host "localhost"
   :port 5432
   :user "usuario"
   :password "1234"})


(deftest testar-conexao
  (testing "Conexão com o banco de dados deve ser bem-sucedida"
    (is (true?
          (try
            (let [resultado (jdbc/query db ["SELECT 1"])]
              (println "Conexão OK! Resultado:" resultado)
              true)
            (catch Exception e
              (println "Erro na conexão:" (.getMessage e))
              false))))))
