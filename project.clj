(defproject gitapi "0.0.1"
  :url "http://github.com/liryanne/gitapi"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.postgresql/postgresql "42.2.20.jre7"]
                 [migratus "1.3.5"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [metosin/reitit "0.5.13"]
                 [com.layerware/hugsql "0.5.1"]
                 [clj-http "3.12.0"]
                 [cheshire "5.10.0"]
                 [metosin/ring-http-response "0.9.2"]
                 [prismatic/schema "1.1.12"]
                 [environ "1.2.0"]
                 [ring/ring-mock "0.4.0"]]
  :resource-paths ["resources"]
  :profiles
    {:cloverage  {:plugins [[lein-cloverage "1.2.2"]]}
     :uberjar {:jar-name     "gitapi.jar"
               :uberjar-name "gitapi-standalone.jar"
               :aot          :all}}
  :repl-options {:init-ns gitapi.core}
  :main gitapi.core)
