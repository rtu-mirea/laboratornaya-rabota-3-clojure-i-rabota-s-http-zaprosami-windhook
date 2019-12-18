(ns metrics-server.api.files
  (:require [metrics-server.core :refer [call-api check-required-params with-collection-format]])
  (:import (java.io File)))

(defn get-files-with-http-info
  "Get files in directory on server"
  []
  (call-api "/files" :get
            {:path-params   {}
             :header-params {}
             :query-params  {}
             :form-params   {}
             :content-types []
             :accepts       []
             :auth-names    []}))

(defn get-files
  "Get files in directory on server"
  []
  (:data (get-files-with-http-info)))

(defn task21 [files]
  (filter (fn [a] (not (get a :directory))) files))

(defn task22 [files]
  (filter (fn [a] (not (get a :executable))) files))

(defn task23 [files]
  (map (fn [file]
         {
          :name (clojure.string/replace (get file :name) #".conf" ".cfg")
          :size (get file :size)
          :changed (get file :changed)
          :directory (get file :directory)
          :executable (get file :executable)
         }
         ) files)
  )

(defn task3 [files]
  (/ (reduce + (map (fn [file] (get file :size)) (filter (fn [a] (not (get a :directory))) files)))
     (count (filter (fn [a] (not (get a :directory))) files)))
  )

(defn -main [& args]
   (println (get-files))
   (println (task21 (get-files)))
   (println (task22 (get-files)))
   (println (task23 (get-files)))
   (println (task3 (get-files)))
)