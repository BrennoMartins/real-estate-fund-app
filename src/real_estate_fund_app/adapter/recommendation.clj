(ns real-estate-fund-app.adapter.recommendation
  (:require [real-estate-fund-app.model.recommendation :as model.recommendation]
            [real-estate-fund-app.wire.in.new_recommendation :as wire.in.new_recommendation]
            [schema.core :as s]))

(s/defn wire-in-recommendation->internal :- model.recommendation/Recommendation
        [data :- wire.in.new_recommendation/new-recommendation-schema]
        data)
