(ns real-estate-fund-app.diplomatic.http-client
  (:require [clj-http.client :as client]
            [real-estate-fund-app.wire.in.list-quotation :as wire.in.list-quotation]
            [clojure.walk :refer [keywordize-keys]]
            [real-estate-fund-app.config :as config]
            [malli.core :as m]))

(defn get-all-quotation-asset
  []
  (let [url config/quotation-url
        response (client/get url {:as :json-string-keys})
        data (keywordize-keys (:body response))]
    (println "Response from API:" data)
    ;(when-not (m/validate wire.in.list-quotation/QuotationListSchema data)
    ;  (println "nao - " (m/explain wire.in.list-quotation/QuotationListSchema data)))
    (if (m/validate wire.in.list-quotation/QuotationListSchema data)
      data
      (throw (ex-info "Response does not match with schema!" {:data data})))
    )
  )

;TODO fazer consulta via by name, e tenho que fazer isso no quotation para receber o nome do ativo
(defn get-quotation-asset-by-name
  []
  (let [url "http://localhost:8084/app/quotation"
        response (client/get url {:as :json-string-keys})
        data (keywordize-keys (:body response))]
    (println "Response from API:" data)
    ;(when-not (m/validate wire.in.list-quotation/QuotationListSchema data)
    ;  (println "nao - " (m/explain wire.in.list-quotation/QuotationListSchema data)))
    (if (m/validate wire.in.list-quotation/QuotationListSchema data)
      data
      (throw (ex-info "Response does not match with schema!" {:data data})))
    )
  )

;TODO listar em um documento tudo que usamos em Clojure
;TODO entender se preciso fazer um adaptador para a consulta da quotation