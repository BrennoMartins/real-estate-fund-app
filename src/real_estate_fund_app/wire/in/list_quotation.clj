(ns real-estate-fund-app.wire.in.list-quotation)

(def QuotationSchema
  [:map
   [:id int?]
   [:name string?]
   [:value number?]
   [:dateLastUpdate {:optional true} [:maybe string?]]
   [:automaticUpdateValue boolean?]])

(def QuotationListSchema
  [:vector QuotationSchema])


;TODO tentar entender onde guardamos os schemas para as buscas em apis