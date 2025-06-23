(ns real-estate-fund-app.controller.recommendation
  (:require [real-estate-fund-app.model.recommendation :as model.recommendation]
            [real-estate-fund-app.model.asset :as model.asset]
            [real-estate-fund-app.wire.out.return_recommendation :as wire.out.return_recommendation]
            [real-estate-fund-app.controller.asset :as controller.asset]
            [schema.core :as s]))


(defn return-item-to-buy
  [list-all-assets]
  (first (sort-by :quantity-fix #(compare %2 %1) list-all-assets))
  )

(defn return-updated-list-after-buy
  [list-all-assets
   asset-to-buy]
  (let [updated-assets (mapv (fn [asset]
                               (if (= (:name-asset asset) (:name-asset asset-to-buy))
                                 (assoc asset
                                   :quantity-asset (+ (:quantity-asset asset) (:quantity-fix asset-to-buy))
                                   :value-asset    (* (:quotation-asset asset) (+ (:quantity-asset asset) (:quantity-fix asset-to-buy)))
                                   :value-total-average-price-asset (* (:value-average-price-asset asset) (+ (:quantity-asset asset) (:quantity-fix asset-to-buy))))
                                 asset))
                             list-all-assets)]
    (controller.asset/update-values-asset-recommendation updated-assets)
  ))

;(s/defn return-options-buy ;:- wire.out.return_recommendation/recommendation-return-schema
;  [db
;   table
;   recommendation-budget :- model.recommendation/Recommendation]
;  (let [list-all-assets (controller.asset/return-all-assets db (name table))
;        asset-to-buy (return-item-to-buy list-all-assets)
;        price-asset (* (:quantity-fix asset-to-buy) (:quotation-asset asset-to-buy))
;        return-recommendation-teste {}]
;
;    (if (> price-asset (:budget recommendation-budget))
;      {:name-asset "nenhuma recomendação" :quantity-asset 0}
;      (do
;        (return-updated-list-after-buy list-all-assets asset-to-buy)
;        (assoc return-recommendation-teste
;          :name-asset (:name-asset asset-to-buy)
;          :quantity-asset (:quantity-fix asset-to-buy)
;          :total price-asset)
;        ))))
;
 (s/defn return-options-buy
  [db
   table
   recommendation-budget :- model.recommendation/Recommendation]
  (let [initial-assets (controller.asset/return-all-assets db (name table))]

    ;; Função recursiva para simular o while
    (loop [remaining-assets initial-assets
           budget (:budget recommendation-budget)
           result []]
      (let [asset-to-buy (return-item-to-buy remaining-assets)
            price-asset (* (:quantity-fix asset-to-buy) (:quotation-asset asset-to-buy))]

        (if (or (nil? asset-to-buy) (> price-asset budget))
          ;; fim da compra
          (if (empty? result)
            {:name-asset "nenhuma recomendação" :quantity-asset 0}
            result)
          ;; continuar comprando
          (let [updated-assets (return-updated-list-after-buy remaining-assets asset-to-buy)
                new-budget (- budget price-asset)
                new-result (conj result {:name-asset (:name-asset asset-to-buy)
                                         :quantity-asset (:quantity-fix asset-to-buy)
                                         :total price-asset})]
            (println new-budget)
            (recur updated-assets new-budget new-result)))))))



(defn metodo-teste
  [valor
   debito]
  (- valor debito)
  )


(defn testar-metodo-teste
  [valor
   debito]
  (let [resultado (metodo-teste valor debito)]
    (println "Resultado do teste:" resultado)
    resultado))


(defn entendendo-loop
  []
  (loop [i 0]                         ;; inicia variável i com 0
    (when (< i 5)                    ;; enquanto i < 5
      (println "i é:" i)            ;; imprime i
      (recur (inc i))))             ;; reinicia o loop com i + 1
  )


(defn exercicio-um-to-dez
  []

  (loop [i 1]                     ;; inicia variável i com 1
    (when (<= i 10)                    ;; enquanto i < 10)
      (println "i é:" i)              ;; imprime i
      (recur (inc i)))                 ;; reinicia o loop com i + 1
    )
  )


(defn soma-ate [n]
  (loop [i 1]
    (when (<= i n)
      (+ )

      ) ;; sua implementação aqui)
    ))

(defn soma-ate [n]
  (loop [i 1
         total 0]
    (if (> i n)
      total
      (recur (inc i) (+ total i)))))


(exercicio-um-to-dez)
