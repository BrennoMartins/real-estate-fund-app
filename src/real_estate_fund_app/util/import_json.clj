(ns real-estate-fund-app.util.import-json
  (:require [cheshire.core :as json]
            [clojure.java.io :as io]))

(defn open-file[file]
  (with-open [reader (io/reader (io/resource file))]
    (json/parse-stream reader true)))