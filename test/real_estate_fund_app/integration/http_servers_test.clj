(ns real-estate-fund-app.integration.http-servers-test
  (:require [clojure.test :refer :all]
            [real-estate-fund-app.controller.asset :as controller.asset]
            [real-estate-fund-app.diplomatic.http-server :as integration.http-servers]))


;TODO corrigir esse test
;(deftest returns-201-when-asset-is-created-successfully
;         (with-redefs [controller.asset/create-new-asset (fn [_ _ _] nil)]
;           (let [req {:body {:id-asset 1 :quantity-asset 10 :quotation-asset 100}}]
;             (is (= 201 (:status response)))
;             (is (= {:mensagem "Asset created successfully"} (:body response))))))

(deftest returns-400-when-asset-creation-data-is-invalid
         (let [req {:body {:id-asset nil :quantity-asset 10 :quotation-asset 100}}
               response (integration.http-servers/app-routes {:request-method :post :uri "/asset/real-estate" :body req})]
           (is (= 400 (:status response)))
           (is (= "Invalid data" (get-in response [:body :erro])))))

(deftest returns-200-with-all-assets-for-real-estate
         (with-redefs [controller.asset/return-all-assets (fn [_ _] [{:id-asset 1 :name-asset "Asset A"}])]
           (let [response (integration.http-servers/app-routes {:request-method :get :uri "/asset/real-estate"})]
             (is (= 200 (:status response)))
             (is (= [{:id-asset 1 :name-asset "Asset A"}] (:body response))))))

(deftest returns-404-for-nonexistent-route
         (let [response (integration.http-servers/app-routes {:request-method :get :uri "/nonexistent-route"})]
           (is (= 404 (:status response)))
           (is (= "Route not found" (:body response)))))