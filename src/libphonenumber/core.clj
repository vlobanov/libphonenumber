(ns libphonenumber.core
  (:import [com.google.i18n.phonenumbers PhoneNumberUtil
                                         PhoneNumberUtil$PhoneNumberFormat
                                         PhoneNumberUtil$PhoneNumberType
                                         NumberParseException]))

(def format-e164 PhoneNumberUtil$PhoneNumberFormat/E164)

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

(defn parse-phone [^String phone ^String country-code]
  (let [number-util (PhoneNumberUtil/getInstance)]
    (try
      (let [parsed (.parse number-util phone country-code)
            number-type (get phone-types
                             (.getNumberType number-util parsed)
                             :unknown)
            is-valid (if (.isValidNumber number-util parsed)
                         :valid
                         :invalid)
            formatted (.format number-util parsed format-e164)]
        [is-valid {:type number-type
                   :formatted formatted}])
      (catch NumberParseException e [:error (str e)]))))