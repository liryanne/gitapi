(ns gitapi.sql
  (:require [gitapi.db :as db]
            [clojure.set :refer [rename-keys]]))

(defn insert-user!
  ([id username]
   (first (db/insert-user db/spec {:id id, :username username}))))

(defn get-user-by-username
  [username]
  (first (db/select-user-by-username db/spec {:username username})))

(defn insert-repository!
  [id name user-id url size stargazers watchers updated-at created-at]
  (let [repository {:id id
                    :name name
                    :user-id user-id
                    :url url
                    :size size
                    :stargazers stargazers
                    :watchers watchers
                    :updated-at updated-at
                    :created-at created-at}]
    (first (db/insert-repository db/spec repository))))

(defn get-repositories-by-user
  [username]
  (db/select-repositories-by-user db/spec {:username username}))

(defn get-repositories-from-local
  [username]
  (let [repositories-db (get-repositories-by-user username)]
    (if-not (empty? repositories-db)
      (let [user (get-user-by-username username)]
        (merge
          (rename-keys user {:id :user_id})
          {:repositories (vec (map #(dissoc % :user_id) repositories-db))})))))


