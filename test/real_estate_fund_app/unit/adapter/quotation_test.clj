(ns real-estate-fund-app.unit.adapter.quotation-test
  (:require [clojure.test :refer :all]
            [real-estate-fund-app.controller.quotation :as controller.quotation ]
            [real-estate-fund-app.diplomatic.http-client :as diplomatic.http-client])
  (:import (clojure.lang ExceptionInfo)))

;TODO teste unitario