(ns real-estate-fund-app.controller.quotation
  (:require [real-estate-fund-app.diplomatic.http-client :as http-client]
            [schema.core :as s]))

(s/defn return-value-one-quotation :- s/Str
  "Return the value of the quotation asset."
  [asset :- s/Str]
  (if-let [quotation (http-client/get-quotation-asset-by-name asset)]
    (:value quotation)
    (throw (ex-info "Quotation not found for asset!" {:asset asset}))))