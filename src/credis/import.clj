(ns credis.import
  (:require [clojure.java.io :as io]
            [clj-redis.client :as red]))

(set! *warn-on-reflection* true) 

(def db (red/init))

(defn trim-quote [s]
  (clojure.string/replace s #"\"" ""))

(defn load-blocks-file 
  "The function should load the GeoLiteCity-Blocks.csv file 
   into the Redis localhost; on the Redis side the sorted set 
   data structure is used. The first two lines are skipped."
  ([] (load-blocks-file  (str (System/getenv "HOME") "/Downloads/GeoLiteCity-Blocks.csv")))
  ([full-path-to-file] 
     (with-open [rdr (io/reader full-path-to-file)]
       (doseq [[idx line] (map-indexed vector (line-seq rdr))]
         (when (> idx 1) 
            (let [[startIp endIp locId] (clojure.string/split line #",")]
               (red/zadd db 
                 "ip2city:"
                 (bigint (trim-quote startIp))
                 (str (trim-quote locId) "_" idx))))))))
                 

(defn load-location-file
  "The function should load the GeoLiteCity-Location.csv file 
   into the Redis localhost; on the Redis side the HASH  
   data structure is used. The first two lines are skipped."
  ([] (load-location-file (str (System/getenv "HOME") "/Downloads/GeoLiteCity-Location.csv")))
  ([full-path-to-file]
     (with-open [rdr (io/reader full-path-to-file)]
       (doseq [[idx line] (map-indexed vector (line-seq rdr))]
         (when (> idx 1)
            (let [[loc-id country region city zip lat long _ _] (clojure.string/split line #",")]
              (red/hmset db
 		(str "cityId2city:" (trim-quote loc-id))
                (hash-map
                   "city" (trim-quote city)
        	   "region" (trim-quote region)
                   "country" (trim-quote country))))))))) 
    
