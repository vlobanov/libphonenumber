# libphonenumber

A thin wrapper for Google's https://github.com/googlei18n/libphonenumber/

## Usage
Lein deps:
```
[me.vlobanov/libphonenumber "0.2.0-SNAPSHOT"]
```

```
(require [libphonenumber.core :refer [parse-phone example-phone]])

(parse-phone "8(985) 1309-309123" "RU") =>
  [:invalid {:type :unknown, :e164 "+79851309309123", :international "+7 9851309309123"}]

(parse-phone "8(985) 309-3091" "RU")  =>
  [:valid {:type :mobile, :e164 "+79853093091", :international "+7 985 309-30-91"}]

(example-phone "US")  =>
  [:valid {:type :fixed-line-or-mobile, :e164 "+12015550123", :international "+1 201-555-0123", :national "(201) 555-0123"}]

; last argument is optional - phone type (:mobile by default)

(libphonenumber.core/example-phone "RU" :fixed-line) =>
   [:valid {:type :fixed-line,
            :e164 "+73011234567",
            :international "+7 301 123-45-67",
            :national "8 (301) 123-45-67"}]
```

## License

Copyright © 2016 Vadim Lobanov

Distributed under the Eclipse Public License version 1.0
