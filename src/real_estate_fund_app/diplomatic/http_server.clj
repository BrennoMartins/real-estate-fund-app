(ns real-estate-fund-app.diplomatic.http-server
  (:require [clojure.java.jdbc :as jdbc]
            [compojure.core :refer [defroutes POST GET]]
            [compojure.route :as route]
            [real-estate-fund-app.controller.asset :as controller.asset]
            [real-estate-fund-app.diplomatic.db.financialdb :as diplomatic.db.financialdb]
            [real-estate-fund-app.adapter.asset :as adapter.asset]
            [real-estate-fund-app.model.asset :as model.asset]
            [real-estate-fund-app.wire.in.create-new-asset :as wire.in.create-new-asset]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [schema.core :as s]))

(defroutes app-routes
           (POST "/asset/real-estate" req
                 (let [body (:body req)
                       valid? (s/check wire.in.create-new-asset/create-new-asset-schema body)]
                   (if valid?
                     {:status 400 :body {:erro "Invalid data" :detalhes valid?}}
                     (do
                       (controller.asset/create-new-asset diplomatic.db.financialdb/db :real_estate_fund (adapter.asset/wire-create-new-asset->internal-asset body))
                       {:status 201 :body {:mensagem "Asset created successfully"}}))))

           (POST "/asset/real-estate/refatorar" req
             (let [body (:body req)
                   valid? (s/check model.asset/asset-schema body)]
               (if valid?
                 {:status 400 :body {:erro "Dados inválidos" :detalhes valid?}}
                 (do
                   (jdbc/insert! diplomatic.db.financialdb/db :real_estate_fund body)
                   {:status 201 :body {:mensagem "Pessoa inserida com sucesso"}}))))

           (GET "/asset/real-estate" []
             (let [assets (controller.asset/return-all-assets diplomatic.db.financialdb/db "real_estate_fund")]
               {:status 200
                :body assets}))

           (route/not-found {:status 404 :body "Route not found"}))

;; Middleware
(def app
  (-> app-routes
      (wrap-json-body {:keywords? true})
      wrap-json-response
      (wrap-defaults api-defaults)))

;; Main
(defn -main []
  (jetty/run-jetty app {:port 3000}))