(ns real-estate-fund-app.controller.quotation
  (:require [real-estate-fund-app.diplomatic.http-client :as diplomatic.http-client]
            [real-estate-fund-app.model.quotation :as model.quotation]
            [malli.core :as m]
            [schema.core :as s]))

(s/defn return-value-quotation :- s/Str
  "Return the value of the quotation asset."
  [asset :- s/Str
   list-quotation :- model.quotation/QuotationListSchema]
  (if-let [quotation (some #(when (= (:name %) asset) %) list-quotation)]
    (:value quotation)
    (throw (ex-info "Quotation not found for asset!" {:asset asset}))))
