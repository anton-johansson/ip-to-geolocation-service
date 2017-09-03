/**
 * Copyright (c) Anton Johansson <antoon.johansson@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.antonjohansson.geolocation.config;

import org.junit.Assert;
import org.junit.Test;

import com.antonjohansson.geolocation.provider.ipapi.IpApiProvider;
import com.antonjohansson.geolocation.source.es.ElasticsearchSource;

/**
 * Unit tests of {@link Configuration}.
 */
public class ConfigurationTest extends Assert
{
    private final Configuration configuration = new Configuration("src/test/resources/config.properties");

    @Test
    public void test()
    {
        configuration.load();

        IpApiProvider provider = new IpApiProvider();
        provider.setEndpoint("https://pro.ip-api.com");
        provider.setKey("abc123");

        ElasticsearchSource source = new ElasticsearchSource();
        source.setEndpoint("http://my-elasticsearch-cluster:9200");
        source.setIndex("*");
        source.setType("customer");
        source.setAddressFieldName("remoteAddress");

        assertEquals(100, configuration.getBatchSize());
        assertEquals(provider, configuration.getProvider());
        assertEquals(source, configuration.getSource());
    }

    @Test(expected = RuntimeException.class)
    public void test_fileNotFound()
    {
        new Configuration("non-existing-file.properties").load();
    }
}
