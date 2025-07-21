(ns real-estate-fund-app.diplomatic.http-client
  (:require [clj-http.client :as client]
            [real-estate-fund-app.wire.in.list-quotation :as wire.in.list-quotation]
            [clojure.walk :refer [keywordize-keys]]
            [real-estate-fund-app.model.quotation :as model.quotation]
            [real-estate-fund-app.config :as config]
            [malli.core :as m]
            [schema.core :as s]))

(s/defn get-quotation-asset-by-name :- model.quotation/Quotation
  [name]
  (let [url (str config/quotation-url name)
        response (client/get url {:as :json-string-keys})
        data (keywordize-keys (:body response))]
    (if (m/validate wire.in.list-quotation/QuotationSchema data)
      data
      (throw (ex-info "Response does not match with schema!" {:data data})))))
