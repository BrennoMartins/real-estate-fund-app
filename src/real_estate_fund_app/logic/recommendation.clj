(ns real-estate-fund-app.logic.recommendation)

(defn return-item-to-buy
  [list-all-assets]
  (first (sort-by :quantity-fix #(compare %2 %1) list-all-assets)))

(defn return-list-after-buy
  [list-all-assets
   asset-to-buy]
  (mapv (fn [asset]
          (if (= (:name-asset asset) (:name-asset asset-to-buy))
            (assoc asset
              :quantity-asset (+ (:quantity-asset asset) (:quantity-fix asset-to-buy))
              :value-asset (* (:quotation-asset asset) (+ (:quantity-asset asset) (:quantity-fix asset-to-buy)))
              :value-total-average-price-asset (* (:value-average-price-asset asset) (+ (:quantity-asset asset) (:quantity-fix asset-to-buy))))
            asset))
        list-all-assets))


(defn group-option-buy
  [list-all-assets]
  (->> list-all-assets
       (group-by :name-asset)
       (map (fn [[name entries]]
              {:name-asset     name
               :quantity-asset (reduce + (map :quantity-asset entries))
               :total          (reduce + (map :total entries))}))))

(defn calculate-price [asset]
        (* (:quantity-fix asset) (:quotation-asset asset)))

(defn should-stop-buying? [asset budget]
        (or (nil? asset)
            (zero? (calculate-price asset))
            (> (calculate-price asset) budget)))

(defn build-buy-result [asset price]
        {:name-asset (:name-asset asset)
         :quantity-asset (:quantity-fix asset)
         :total price})