(ns libphonenumber.core-test
  (:require [clojure.test :refer :all]
            [libphonenumber.core :refer :all]))

(deftest a-test
  (testing "some basics"
    (is (= :invalid (first (parse-phone "8(985) 1309-309123" "RU"))))
    (is (= :valid (first (parse-phone "8(985) 3093091" "RU"))))))
