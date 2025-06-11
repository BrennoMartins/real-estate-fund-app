(ns real-estate-fund-app.model.quotation)

(def Quotation
  [:map
   [:id int?]
   [:name string?]
   [:value number?]
   [:dateLastUpdate {:optional true} [:maybe string?]]
   [:automaticUpdateValue boolean?]])

(def QuotationListSchema
  [:vector Quotation])