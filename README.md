# IP to Geo-Location service

A service that takes data containing IP addresses from a given source and updates the geo-location using a given provider.


## Sample configuration

The following sample uses an Elasticsearch source and the IP-API provider.

```
provider = com.antonjohansson.geolocation.provider.ipapi.IpApiProvider
provider.token = abc123
source = com.antonjohansson.geolocation.source.es.ElasticsearchSourc
```


## License

Apache License © [Anton Johansson](https://github.com/anton-johansson)
