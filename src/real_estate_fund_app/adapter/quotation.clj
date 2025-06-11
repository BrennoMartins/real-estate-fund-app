(ns real-estate-fund-app.adapter.quotation
  (:require [real-estate-fund-app.model.quotation :as model.quotation]
            [real-estate-fund-app.wire.in.list-quotation :as wire.in.list-quotation]
            [schema.core :as s]))


;TODO no futuro pensar em trabalhar com esse mapa para deixar apenas um retorno
(s/defn wire-in-quotation->internal :- model.quotation/QuotationListSchema
  [data :- wire.in.list-quotation/QuotationListSchema]
  data)


