(ns gitapi.core
  (:require [migratus.core :as migratus]
            [ring.adapter.jetty :refer [run-jetty]]
            [reitit.ring :as ring]
            [gitapi.routes :refer :all]
            [gitapi.db :as db])
  (:gen-class :main true))

(def app (ring/ring-handler
           router
           (ring/create-default-handler
             {:not-found (constantly {:status 404, :body "not-found"})
              :method-not-allowed (constantly {:status 405, :body "method-not-allowed"})
              :not-acceptable (constantly {:status 406, :body "not-acceptable"})})))

(defn -main
  [& _]
  (migratus/init db/config)
  (run-jetty #'app {:port 3000 :join? false})
  (println "server running on port 3000"))
