(defproject short-seller-parser "0.1.0"
  :description "Tool for parsing current short sellers from FIVA"
  :url "https://github.com/stormaaja/short-seller-parser"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                  [hickory "0.7.1"]
                  [clj-http "3.6.1"]]
  :main ^:skip-aot short-seller-parser.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
