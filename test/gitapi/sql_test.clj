(ns gitapi.sql-test
  (:require [clojure.test :refer :all]
            [gitapi.sql :as sql]
            [gitapi.test-utils :as tu]
            [gitapi.schemas :as schemas]
            [schema.core :as s]))

(use-fixtures :once tu/test-db-fixture)

(deftest insert-user!-test
  (testing "Adding user"
    (is (= (sql/insert-user! 999 "maria")
          {:id 999})))

  (testing "Getting user by username"
    (is (= (:id (sql/get-user-by-username "maria"))
          999))))

(deftest insert-repository!-test
  (testing "Adding repository"
    (sql/insert-user! 111 "bob")
    (is (= (sql/insert-repository! 12345 "repository1" 111 "url.com" 1 2 3 "2021-04-22T03:17:55Z" "2020-09-16T18:52:49Z")
          {:id 12345})))

  (testing "Getting repositories by user"
    (is (= (count (sql/get-repositories-by-user "bob"))
          1)))

  (testing "Getting repositories from local"
    (let [repositories (sql/get-repositories-from-local "bob")]
      (s/validate schemas/RepositoriesSchema repositories)

      (is (empty? (sql/get-repositories-from-local "foo"))))))
