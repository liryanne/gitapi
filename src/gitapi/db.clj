(ns gitapi.db
  (:require [hugsql.core :as hugsql]
            [environ.core :refer [env]]))

(def spec {:dbtype "postgresql"
           :connection-uri "jdbc:postgresql://postgres:5432/postgres"
           :user "postgres"
           :password (env :db-password)})

(def config
  {:store :database
   :migration-dir "migrations/"
   :init-in-transaction? false
   :db spec
   :init-script "init.sql"})

(hugsql/def-db-fns "gitapi/sql/repository.sql")
(hugsql/def-db-fns "gitapi/sql/user.sql")
