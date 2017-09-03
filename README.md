# IP to Geo-Location service

[![Build Status](https://img.shields.io/travis/anton-johansson/ip-to-geolocation-service/master.svg)](https://travis-ci.org/anton-johansson/ip-to-geolocation-service)
[![License](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)](https://raw.githubusercontent.com/anton-johansson/ip-to-geolocation-service/master/LICENSE)

A service that takes data containing IP addresses from a given source and updates the geo-location using a given provider.


## Sources and providers

Sources:
 * [Elasticsearch](https://www.elastic.co/products/elasticsearch)

Providers:
 * [IP API](http://ip-api.com)


## Sample configuration

The following sample uses an Elasticsearch source and the IP-API provider.

```
provider = com.antonjohansson.geolocation.provider.ipapi.IpApiProvider
provider.key = abc123

source = com.antonjohansson.geolocation.source.es.ElasticsearchSource
source.endpoint = http://my-elasticsearch-cluster:9200
source.index = *
source.type = customer
source.addressFieldName = remoteAddress
```


## License

Apache License © [Anton Johansson](https://github.com/anton-johansson)
