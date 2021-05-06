(ns gitapi.handlers
  (:require [gitapi.git :as git]
            [ring.util.http-response :refer :all]
            [gitapi.sql :refer [get-repositories-from-local]]
            [clojure.set :refer [rename-keys]]))

(defn get-repositories-handler
  [{:keys [username from_local]}]
  (let [from-local (Boolean/valueOf from_local)
        repositories (if (true? from-local)
                       (get-repositories-from-local username)
                       (git/get-repositories username))]
    (ok (if (empty? repositories)
          {}
          repositories))))

(defn get-repository-handler
  [{:keys [username repository save_data]}]
  (if (nil? save_data)
    (bad-request {:message "Parameter save_data required"})

    (let [save-data (Boolean/valueOf save_data)
          repo (git/get-repository username repository save-data)]
      (ok repo))))



