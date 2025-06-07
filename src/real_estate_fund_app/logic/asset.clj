(ns real-estate-fund-app.logic.asset)


(defn return-index-asset
  [quotation avarage-price]
;BigDecimal percent = quotation.divide(averagePrice, 2, RoundingMode.HALF_UP).subtract(BigDecimal.ONE);
;return percent.multiply(new BigDecimal("100"));
    (let [percent (/ (- quotation avarage-price) avarage-price)]
      (* percent 100)
      )
  )

















;@Override
;public BigDecimal differenceCalculate(BigDecimal quotation, BigDecimal averagePrice) {
;                                                                                      return quotation.subtract(averagePrice);
;                                                                                      }
;
;@Override
;public BigDecimal indexCalculate(BigDecimal quotation, BigDecimal averagePrice) {

;                                                                                 }
;
;@Override
;public BigDecimal valueCalculate(BigDecimal quantity, BigDecimal quotation) {
;                                                                             return quantity.multiply(quotation);
;                                                                             }
;
;public Asset calculatedValues(Asset asset){
;                                           if(asset.getQuotation() == null){
;                                                                            //TODO Buscar Cotação
;                                                                            asset.setQuotation(BigDecimal.valueOf(0));
;                                                                            }
;                                           asset.setDifference(differenceCalculate(asset.getQuotation(), asset.getAveragePrice()));
;                                           asset.setIndex(indexCalculate(asset.getQuotation(), asset.getAveragePrice()));
;                                           asset.setValue(valueCalculate(asset.getQuantity(), asset.getQuotation()));
;                                           return asset;
;                                           }
