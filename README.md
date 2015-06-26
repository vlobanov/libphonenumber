# libphonenumber

A thin wrapper for Google's https://github.com/googlei18n/libphonenumber/

## Usage
Lein deps:
```
[me.vlobanov/libphonenumber "0.1.1-SNAPSHOT"]
```

```
(require [libphonenumber.core :refer [parse-phone]])

(parse-phone "8(985) 1309-309123" "RU") =>
  [:invalid {:type :unknown, :e164 "+79851309309123", :international "+7 9851309309123"}]

(parse-phone "8(985) 309-3091" "RU")  =>
  [:valid {:type :mobile, :e164 "+79853093091", :international "+7 985 309-30-91"}]
```

## License

Copyright © 2015 Vadim Lobanov

Distributed under the Eclipse Public License version 1.0