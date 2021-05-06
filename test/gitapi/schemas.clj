(ns gitapi.schemas
  (:require [clojure.test :refer :all]
            [schema.core :as s]))

(def RepositoriesSchema
  {:user_id s/Int
   :username s/Str
   :repositories [{:id s/Int
                   :url s/Str
                   :watchers s/Int
                   :name s/Str
                   :stargazers s/Int
                   :size s/Int
                   :updated_at s/Str
                   :created_at s/Str}]})

(def RepositorySchema
  {:id s/Int
   :url s/Str
   :watchers s/Int
   :name s/Str
   :stargazers s/Int
   :size s/Int
   :updated_at s/Str
   :created_at s/Str})
