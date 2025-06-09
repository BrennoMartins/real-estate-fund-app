(ns real-estate-fund-app.adapter.quotation
  (:require [real-estate-fund-app.diplomatic.http-client :as diplomatic.http-client]
            [malli.core :as m]))


;TODO verificar se a consulta aqui deveria ficar no logic ou aqui no adapter


;;TODO prioritaria, fazer a logica do asset para retornar o valor da quotation
(defn return-value-quotation)
  "Return the value of the quotation asset."
  [asset]
  (let [list-quotation (diplomatic.http-client/get-all-quotation-asset)])
    (if (and quotation
             (not (empty? quotation)))
      (:value quotation)
      )))
