(ns real-estate-fund-app.integration.insert-asset
  (:require [clojure.test :refer :all]))


;[
; {
;  "id-asset": 1,
;  "name-asset": "MCCI11",
;  "quantity-asset": 29,
;  "value-average-price-asset": 85.16,
;  "percent-recommendation": 9.0
;  },
; {
;  "id-asset": 2,
;  "name-asset": "CPTS11",
;  "quantity-asset": 332,
;  "value-average-price-asset": 7.39,
;  "percent-recommendation": 9.0
;  },
; {
;  "id-asset": 3,
;  "name-asset": "RBRR11",
;  "quantity-asset": 31,
;  "value-average-price-asset": 86.0,
;  "percent-recommendation": 10.0
;  },
; {
;  "id-asset": 4,
;  "name-asset": "KNCR11",
;  "quantity-asset": 19,
;  "value-average-price-asset": 103.15,
;  "percent-recommendation": 7.5
;  },
; {
;  "id-asset": 5,
;  "name-asset": "XPCI11",
;  "quantity-asset": 28,
;  "value-average-price-asset": 79.7,
;  "percent-recommendation": 8.0
;  },
; {
;  "id-asset": 6,
;  "name-asset": "CVBI11",
;  "quantity-asset": 12,
;  "value-average-price-asset": 86.25,
;  "percent-recommendation": 4.0
;  },
; {
;  "id-asset": 8,
;  "name-asset": "BTLG11",
;  "quantity-asset": 19,
;  "value-average-price-asset": 100.9,
;  "percent-recommendation": 7.0
;  },
; {
;  "id-asset": 9,
;  "name-asset": "LVBI11",
;  "quantity-asset": 8,
;  "value-average-price-asset": 102.03,
;  "percent-recommendation": 3.0
;  },
; {
;  "id-asset": 10,
;  "name-asset": "BRCO11",
;  "quantity-asset": 10,
;  "value-average-price-asset": 109.01,
;  "percent-recommendation": 4.0
;  },
; {
;  "id-asset": 11,
;  "name-asset": "XPLG11",
;  "quantity-asset": 12,
;  "value-average-price-asset": 98.55,
;  "percent-recommendation": 4.5
;  },
; {
;  "id-asset": 12,
;  "name-asset": "PVBI11",
;  "quantity-asset": 21,
;  "value-average-price-asset": 77.42,
;  "percent-recommendation": 6.0
;  },
; {
;  "id-asset": 13,
;  "name-asset": "TEPP11",
;  "quantity-asset": 10,
;  "value-average-price-asset": 83.49,
;  "percent-recommendation": 3.0
;  },
; {
;  "id-asset": 14,
;  "name-asset": "XPML11",
;  "quantity-asset": 31,
;  "value-average-price-asset": 105.12,
;  "percent-recommendation": 11.5
;  },
; {
;  "id-asset": 15,
;  "name-asset": "RBRF11",
;  "quantity-asset": 230,
;  "value-average-price-asset": 7.02,
;  "percent-recommendation": 6.0
;  },
; {
;  "id-asset": 16,
;  "name-asset": "KNSC11",
;  "quantity-asset": 172,
;  "value-average-price-asset": 8.78,
;  "percent-recommendation": 5.5
;  },
; {
;  "id-asset": 17,
;  "name-asset": "VGIR11",
;  "quantity-asset": 69,
;  "value-average-price-asset": 9.58,
;  "percent-recommendation": 2.0
;  }
; ]
