(ns gitapi.git-test
  (:require [clojure.test :refer :all]
            [gitapi.git :refer :all]
            [gitapi.test-utils :as tu]))

(use-fixtures :once tu/test-db-fixture)

(deftest parse-test
  (is (= (parse "{\"foo\":\"bar\"}")
        {:foo "bar"})))

(def user-test
  {:id 36158974, :repos_url "https://api.github.com/users/liryanne/repos", :username "liryanne"})

(def repository-test
  {:html_url "https://github.com/liryanne/gitapi",
   :private false,
   :name "gitapi",
   :id 364992996,
   :created_at "2021-05-06T17:50:01Z"})

(deftest get-user-test
  (is (= (get-user "liryanne")
        user-test))
  (is (empty? (get-user ""))))

(deftest get-repositories-test
  (is (= (:id (get-repositories "liryanne"))
        (:user-id user-test))))

(deftest get-repository-test
  (is (= (:id (get-repository "liryanne" "gitapi" false))
        (:id repository-test))))
