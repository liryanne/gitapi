(ns gitapi.test-utils
  (:require [clojure.test :refer :all]
            [gitapi.db :as db]
            [migratus.core :as migratus]))

(def test-db-spec {:dbtype "postgresql"
                   :dbname "postgres"
                   :user "postgres"
                   :password "postgres"})

(def config
  {:store :database
   :migration-dir "migrations/"
   :init-in-transaction? false
   :db test-db-spec
   :init-script "init.sql"})

(defn test-db-fixture
  [tests]
  (with-redefs [db/spec test-db-spec]
    (migratus/init config)
    (tests)))
