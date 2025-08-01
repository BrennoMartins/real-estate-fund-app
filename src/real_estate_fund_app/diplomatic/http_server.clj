(ns real-estate-fund-app.diplomatic.http-server
  (:require [compojure.core :refer [GET POST PUT defroutes]]
            [compojure.route :as route]
            [real-estate-fund-app.adapter.asset :as adapter.asset]
            [real-estate-fund-app.adapter.recommendation :as adapter.recommendation]
            [real-estate-fund-app.controller.asset :as controller.asset]
            [real-estate-fund-app.controller.recommendation :as controller.recommendation]
            [real-estate-fund-app.diplomatic.db.financialdb :as diplomatic.db.financialdb]
            [real-estate-fund-app.wire.in.buy-asset :as wire.in.buy-asset]
            [real-estate-fund-app.wire.in.create-new-asset :as wire.in.create-new-asset]
            [real-estate-fund-app.wire.in.new_recommendation :as wire.in.new_recommendation]
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

           (GET "/asset/real-estate" []
             (let [assets (controller.asset/return-all-assets diplomatic.db.financialdb/db "real_estate_fund")]
               {:status 200
                :body   assets}))

           (POST "/asset/real-estate/update" []
             (controller.asset/update-quotation-asset diplomatic.db.financialdb/db "real_estate_fund")
             {:status 201
              :body   "All assets updated successfully"})

           (POST "/asset/real-estate/reset" []
             (controller.asset/reset-assets diplomatic.db.financialdb/db "real_estate_fund")
             {:status 201
              :body   "Reset successfully"})

           (PUT "/asset/real-estate/buy/:id-asset" [id-asset :as req]
             (let [body (assoc (:body req) :id-asset (Integer/parseInt id-asset))
                   valid? (s/check wire.in.buy-asset/buy-asset-schema body)]
               (if valid?
                 {:status 400 :body {:error "Missing required fields"}}
                 (do
                   (controller.asset/buying-asset diplomatic.db.financialdb/db "real_estate_fund" (adapter.asset/wire-buy-asset->internal-asset body))
                     {:status 201 :body {:mensagem "Asset created successfully"}}))))

           (GET "/asset/buy-recommendation" [budget update-asset :as req]
             (if (nil? budget)
               {:status 400
                :body   {:erro "Please provide a 'budget' parameter"}}
               (try
                 (let [budget-decimal (BigDecimal. budget)
                       body {:budget budget-decimal
                             :update-asset update-asset}
                       valid? (s/check wire.in.new_recommendation/new-recommendation-schema body)]
                   (if valid?
                     {:status 400
                      :body   {:erro "Invalid data" :detalhes valid?}}
                     {:status 200
                      :body   (controller.recommendation/group-return-option-buy
                                diplomatic.db.financialdb/db
                                "real_estate_fund"
                                (adapter.recommendation/wire-in-recommendation->internal  body))}))
                 (catch Exception e
                   {:status 400
                    :body   {:erro     "Invalid budget value"
                             :detalhes (.getMessage e)}}))))

           (route/not-found {:status 404 :body "Route not found"}))

(def app
  (-> app-routes
      (wrap-json-body {:keywords? true})
      wrap-json-response
      (wrap-defaults api-defaults)))

(defn -main []
  (jetty/run-jetty app {:port 3000}))