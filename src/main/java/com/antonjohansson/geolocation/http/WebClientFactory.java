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
package com.antonjohansson.geolocation.http;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

/**
 * Factory used to build {@link WebClient web clients}.
 */
public final class WebClientFactory
{
    private final List<Object> providers = new ArrayList<>();
    private final String endpoint;
    private MediaType accept;
    private MediaType contentType;

    private WebClientFactory(String endpoint)
    {
        this.endpoint = endpoint;
    }

    /**
     * Starts building a new {@link WebClient}.
     *
     * @param endpoint The endpoint to contact.
     * @return Returns the builder.
     */
    public static WebClientFactory endpoint(String endpoint)
    {
        return new WebClientFactory(endpoint);
    }

    /**
     * Configures the client to send and accept JSON data.
     * 
     * @return Returns the buider itself.
     */
    public WebClientFactory json()
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.setMapper(mapper);
        providers.add(provider);

        contentType = MediaType.APPLICATION_JSON_TYPE;
        accept = MediaType.APPLICATION_JSON_TYPE;

        return this;
    }

    /**
     * Builds the client.
     *
     * @return Returns the built client.
     */
    public WebClient build()
    {
        WebClient client = WebClient.create(endpoint, providers);
        Optional.ofNullable(contentType).ifPresent(client::type);
        Optional.ofNullable(accept).ifPresent(client::accept);
        return client;
    }
}
