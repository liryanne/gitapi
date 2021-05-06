(ns gitapi.routes
  (:require [reitit.ring :as ring]
            [gitapi.handlers :refer :all]
            [reitit.ring :as ring]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.exception :as exception]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.parameters :as parameters]
            [reitit.ring.coercion :as coercion]
            [muuntaja.core :as m]
            [reitit.coercion.spec])
  (:use [clojure.walk]))

(def routes
  [["/repositories"
    {:get {:parameters {:query-params {:username string?
                                       :from_local boolean?}}
           :handler (fn [{:keys [query-params]}]
                      (get-repositories-handler (keywordize-keys query-params)))}}]

   ["/repositories/:username/:repository"
    {:get {:parameters {:query-params {:save_data boolean?}}
           :handler (fn [{:keys [path-params query-params]}]
                      (get-repository-handler (merge path-params (keywordize-keys query-params))))}}]])

(def router
  (ring/router routes
    {:data {:coercion reitit.coercion.spec/coercion
            :muuntaja m/instance
            :middleware [muuntaja/format-negotiate-middleware
                         muuntaja/format-response-middleware
                         exception/exception-middleware
                         muuntaja/format-request-middleware
                         coercion/coerce-request-middleware
                         coercion/coerce-response-middleware
                         parameters/parameters-middleware]}}))
