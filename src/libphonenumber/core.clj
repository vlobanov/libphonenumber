(ns libphonenumber.core
  (:import [com.google.i18n.phonenumbers PhoneNumberUtil
                                         Phonenumber$PhoneNumber
                                         PhoneNumberUtil$PhoneNumberFormat
                                         PhoneNumberUtil$PhoneNumberType
                                         NumberParseException])
  (:require [clojure.set :refer [map-invert]]))

(def format-e164 PhoneNumberUtil$PhoneNumberFormat/E164)
(def format-international PhoneNumberUtil$PhoneNumberFormat/INTERNATIONAL)
(def format-national PhoneNumberUtil$PhoneNumberFormat/NATIONAL)

(def phone-types
  {PhoneNumberUtil$PhoneNumberType/FIXED_LINE :fixed-line
   PhoneNumberUtil$PhoneNumberType/MOBILE :mobile
   PhoneNumberUtil$PhoneNumberType/FIXED_LINE_OR_MOBILE :fixed-line-or-mobile
   PhoneNumberUtil$PhoneNumberType/TOLL_FREE :toll-free
   PhoneNumberUtil$PhoneNumberType/PREMIUM_RATE :premium-rate
   PhoneNumberUtil$PhoneNumberType/SHARED_COST :shared-cost
   PhoneNumberUtil$PhoneNumberType/VOIP :voip
   PhoneNumberUtil$PhoneNumberType/PERSONAL_NUMBER :personal-number
   PhoneNumberUtil$PhoneNumberType/PAGER :pager
   PhoneNumberUtil$PhoneNumberType/UAN :uan
   PhoneNumberUtil$PhoneNumberType/VOICEMAIL :voicemail
   PhoneNumberUtil$PhoneNumberType/UNKNOWN :unknown})

(def phone-types-by-kw (map-invert phone-types))

(defn- format-parsed [^PhoneNumberUtil number-util
                      ^Phonenumber$PhoneNumber parsed]
  (let [is-valid (if (.isValidNumber number-util parsed)
                         :valid
                         :invalid)
        number-type (get phone-types
                             (.getNumberType number-util parsed)
                             :unknown)]
    [is-valid {:type number-type
               :e164 (.format number-util parsed format-e164)
               :international (.format number-util parsed format-international)
               :national (.format number-util parsed format-national)}]))

(defn parse-phone [^String phone ^String country-code]
  (let [number-util (PhoneNumberUtil/getInstance)]
    (try
      (let [parsed (.parse number-util phone country-code)]
        (format-parsed number-util parsed))
      (catch NumberParseException e [:error (str e)]))))

(defn example-phone
  ([^String country-code]
   (example-phone country-code :mobile))
  ([^String country-code phone-type]
   (let [number-util (PhoneNumberUtil/getInstance)
         p-type (phone-types-by-kw phone-type)]
     (if-not p-type
       (throw (ex-info (str "phone-type " phone-type " not found")
                       {:phone-type phone-type
                        :available-types (keys phone-types-by-kw)})))
     (if-let [parsed (.getExampleNumberForType number-util
                                               country-code
                                               p-type)]
       (format-parsed number-util parsed)
       [:error "country code invalid"]))))
