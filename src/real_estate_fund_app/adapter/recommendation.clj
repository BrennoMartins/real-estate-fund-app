(ns real-estate-fund-app.adapter.recommendation
  (:require [real-estate-fund-app.model.recommendation :as model.recommendation]
            [real-estate-fund-app.wire.in.list-quotation :as wire.in.list-quotation]
            [schema.core :as s]))

(s/defn wire-in-recommendation->internal :- model.recommendation/Recommendation
        [data :- wire.in.list-quotation/QuotationListSchema]
        data)
