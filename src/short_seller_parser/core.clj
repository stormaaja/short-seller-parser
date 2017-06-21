(ns short-seller-parser.core
  (:require [hickory.core :refer :all]
            [hickory.select :as s]
            [clj-http.client :as client])
  (:gen-class))

(defn get-page-as-hickory
  [url]
   (->
     (client/get url)
     :body parse as-hickory))

(defn parse-finnish-double
  [str]
  (try
    (Double/parseDouble (clojure.string/replace str "," "."))
    (catch Exception e nil)))

(defn get-first-content
  [row class]
  (first (:content (first (s/select (s/class class) row)))))

(defn get-second-content
  [row class]
  (first (:content (second (s/select (s/class class) row)))))

(defn clean-row
  [row]
  {:stock (get-first-content row "cell_1")
    :position
      (parse-finnish-double (get-second-content row "cell_2"))})

(defn select-table-rows
  [page]
  (->
    (s/select
      (s/child
        (s/class "grid_rowstyle"))
      page)))

(defn get-clean-table-rows
  [page]
  (map clean-row (select-table-rows page)))

(defn group-by-stock
  [col]
  (group-by :stock col))

(defn sum-positions
  [stock]
  {:name (first stock)
    :position (reduce + (map :position (second stock)))})

(defn get-total-positions
  [page]
  (map
    sum-positions
    (group-by-stock (get-clean-table-rows page))))

(defn -main
  [& args]
  (prn
    (get-total-positions
      (get-page-as-hickory (first args)))))
