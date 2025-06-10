(ns real-estate-fund-app.adapter.quotation
  (:require [real-estate-fund-app.diplomatic.http-client :as diplomatic.http-client]
            [malli.core :as m]
            [schema.core :as s]))


;TODO verificar se a consulta aqui deveria ficar no logic ou aqui no adapter


(s/defn return-value-quotation :- s/Str
  "Return the value of the quotation asset."
  [asset :- s/Str]
  (let [list-quotation (diplomatic.http-client/get-all-quotation-asset)]
    (if-let [quotation (some #(when (= (:name %) asset) %) list-quotation)]
      (:value quotation)
      (throw (ex-info "Quotation not found for asset!" {:asset asset})))))
