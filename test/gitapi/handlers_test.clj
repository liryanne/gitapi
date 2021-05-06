(ns gitapi.handlers-test
  (:require [clojure.test :refer :all]
            [gitapi.handlers :refer :all]
            [ring.util.http-status :as status]
            [gitapi.test-utils :as tu]
            [gitapi.schemas :as schemas]
            [gitapi.git-test :as git-test]
            [schema.core :as s]
            [gitapi.db :as db]))

(use-fixtures :once tu/test-db-fixture)

(deftest get-repositories-handler-from-github-test
  (let [response (get-repositories-handler {:username "liryanne"})]

    (testing "Getting repositories from github by username - testing status"
      (is (= (:status response)
            status/ok)))

    (testing "Getting repositories from github by username - testing schema"
      (s/validate schemas/RepositoriesSchema (:body response)))

    (testing "Getting repositories from github by username - testing from_local = false"
      (is (= (get-repositories-handler {:username "liryanne" :from-local false})
            response)))))

(def user-test {:id 111 :username "bob"})

(def repository-test {:id 12345
                      :name "repository1"
                      :user-id 111
                      :url "url.com"
                      :size 1
                      :stargazers 2
                      :watchers 3
                      :updated-at "2021-04-22T03:17:55Z"
                      :created-at "2020-09-16T18:52:49Z"})

(deftest get-repositories-handler-from-local-test
  (db/insert-user tu/test-db-spec user-test)
  (db/insert-repository tu/test-db-spec repository-test)

  (let [response (get-repositories-handler {:username "bob" :from-local true})]
    (testing "Getting repositories from local by username - testing status"
      (s/validate schemas/RepositoriesSchema (:body response)))

    (testing "Getting repositories from local by username - testing schema"
      (is (= (:status response)
            status/ok))))

  (testing "Getting repositories from local by username - testing not found"
    (is (nil? (:user_id (:body (get-repositories-handler {:username "abc1234zzzz" :from-local true})))))))

(deftest get-repository-handler-test
  (testing "Getting repository - testing required parameter save_data"
    (is (= (:status (get-repository-handler {:username "liryanne" :repository "gitapi"}))
          status/bad-request))))

(deftest get-repository-handler-save-data-false-test
  (let [response (get-repository-handler {:username "gfredericks" :repository "4clojure" :save_data false})]

    (testing "Getting repository - testing status"
      (is (= (:status response)
            status/ok)))

    (testing "Getting repository - testing schema"
      (s/validate schemas/RepositorySchema (:body response)))

    (testing "Getting repository - testing db"
      (is (nil? (first (db/select-repository-by-id tu/test-db-spec {:id (get-in response [:body :id])})))))))

(deftest get-repository-handler-save-data-true-test
  (let [response (get-repository-handler {:username "liryanne" :repository "gitapi" :save_data true})]

    (testing "Getting repository - testing status"
      (is (= (:status response)
            status/ok)))

    (testing "Getting repository - testing schema"
      (s/validate schemas/RepositorySchema (:body response)))

    (testing "Getting repository - testing db"
      (is (= (:id (first (db/select-repository-by-id tu/test-db-spec {:id (get-in response [:body :id])})))
            (:id git-test/repository-test))))))
