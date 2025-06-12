(ns real-estate-fund-app.util.convert
  (:require [clojure.string :as str]
            [schema.core :as s]))


(defn kebab->snake [k]
  (-> k
      name
      (str/replace "-" "_")
      keyword))

(defn schema-keys-to-snake-case [schema]
  (into {}
        (map (fn [[k v]] [(kebab->snake k) v]) schema)))