(defproject me.vlobanov/libphonenumber "8.10.18"
  :description "A thin wrapper for Google's libphonenumber"
  :url "https://github.com/vlobanov/libphonenumber"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :global-vars {*warn-on-reflection* true}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.googlecode.libphonenumber/libphonenumber "8.10.18"]])
