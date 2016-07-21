# credis

A Clojure library designed to populate the Redis repository
with the IP-to-city database available on the
http://dev.maxmind.com/geoip/legacy/geolite/ website.

The idea is coming from the 'Redis in action' book published by Manning.
The book URL is https://www.manning.com/books/redis-in-action.



## Usage

Download from the http://dev.maxmind.com/geoip/legacy/geolite/ website the GeoLiteCity-latest.zip file
and unzip it in the standard Downloads folder  - every user account should have one.
In the REPL the folowing commands will load the files:
(use 'credis.import :reload-all)

(load-blocks-file) or (load-blocks-file "the-full-path-to-the-geo-blocks.csv")

(load-location-file) or (load-location-file "the-full-path-to-the-geo-locatio.csv")


## License

Copyright © 2016 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
