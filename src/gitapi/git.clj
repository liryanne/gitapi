(ns gitapi.git
  (:require [clj-http.client :as http]
            [clojure.set :refer [rename-keys]]
            [cheshire.core :as json]
            [gitapi.sql :as sql]))

(defn parse
  [data]
  (json/parse-string data (fn [k] (keyword k))))

(defn get-http
  [url]
  (try
    (parse (:body (http/get url)))
    (catch Exception e
      (println (str "get-http error: " e))
      nil)))

(defn get-user
  [username]
  (let [url (str "https://api.github.com/users/" username)
        user (get-http url)]
    (-> user
      (select-keys [:id :repos_url :login])
      (rename-keys {:login :username}))))

(def keys-repo [:id :name :html_url :watchers :size :updated_at :stargazers_count :created_at])

(defn format-repositories
  [repositories]
  (vec (map #(rename-keys (select-keys % keys-repo) {:html_url :url, :stargazers_count :stargazers}) repositories)))

(defn get-repositories
  [username]
  (let [{url :repos_url
         username :username
         user-id :id} (get-user username)
        repositories (get-http url)]
    {:user_id user-id
     :username username
     :repositories (format-repositories repositories)}))

(defn get-repository
  [username repository-name save-data]
  (let [data (get-repositories username)
        {:keys [user_id username]} data
        repositories (:repositories data)
        repository (first (filter #(= (:name %) repository-name) repositories))]
    (if (true? save-data)
      (do
        (sql/insert-user! user_id username)
        (sql/insert-repository!
          (:id repository)
          (:name repository)
          user_id
          (:url repository)
          (:size repository)
          (:stargazers repository)
          (:watchers repository)
          (:updated_at repository)
          (:created_at repository))))
    repository))
