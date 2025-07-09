(defproject real-estate-fund-app "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [compojure "1.7.0"]
                 [cheshire "5.12.0"]
                 [ring/ring-defaults "0.3.4"]
                 [ring/ring-json "0.5.1"]
                 [ring/ring-jetty-adapter "1.10.0"]
                 [prismatic/schema "1.1.12"]
                 [org.clojure/java.jdbc "0.7.12"]
                 [clj-http "3.12.3"]
                 [metosin/malli "0.14.0"]
                 [org.postgresql/postgresql "42.7.3"]]
  :repl-options {:init-ns real-estate-fund-app.diplomatic.http-server}
  :main real-estate-fund-app.diplomatic.http-server)
