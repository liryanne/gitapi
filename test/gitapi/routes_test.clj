(ns gitapi.routes-test
  (:require [clojure.test :refer :all]
            [gitapi.test-utils :as tu]
            [gitapi.core :refer [app]]
            [ring.mock.request :as mock]
            [ring.util.http-status :as status]))

(use-fixtures :once tu/test-db-fixture)

(deftest route-repositories-username-test
  (is (= (:status (app (mock/request :get "/repositories?username=liryanne")))
        status/ok)))

(deftest route-repositories-save-data-test
  (testing "Without parameter save_data"
    (is (= (:status (app (mock/request :get "/repositories/liryanne/gitapi")))
          status/bad-request))))
