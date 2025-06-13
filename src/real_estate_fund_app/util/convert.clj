(ns real-estate-fund-app.util.convert
  (:require [clojure.string :as str]
            [schema.core :as s]))


(defn kebab->snake [k]
  (-> k
      name
      (str/replace "-" "_")
      keyword))

(defn snake->kebab [k]
  (-> k
      name
      (str/replace "_" "-")
      keyword))

(defn schema-keys-to-snake-case [schema]
  (into {}
        (map (fn [[k v]] [(kebab->snake k) v]) schema)))

(defn schema-keys-to-kebab-case [schema]
  (println schema)
  (into {}
        (map (fn [[k v]] [(snake->kebab k) v]) schema)))

(schema-keys-to-kebab-case {:id_asset 1
                           :name_asset "Asset Name"
                           :value_asset 1000.0
                           :dateLastUpdate "2023-10-01"
                           :automaticUpdateValue true})