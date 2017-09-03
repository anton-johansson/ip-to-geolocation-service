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
package com.antonjohansson.geolocation;

import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;

import com.antonjohansson.geolocation.config.Configuration;
import com.antonjohansson.geolocation.framework.Provider;
import com.antonjohansson.geolocation.framework.Source;
import com.antonjohansson.geolocation.framework.domain.LookupResult;
import com.antonjohansson.geolocation.framework.domain.SourceData;

/**
 * Contains the main entry-point of the application.
 */
public class EntryPoint
{
    private final Configuration configuration;

    public EntryPoint(String configurationFile)
    {
        configuration = new Configuration(configurationFile);
    }

    /**
     * Runs the application.
     */
    public void run()
    {
        configuration.load();

        Provider provider = configuration.getProvider();
        Source<? extends SourceData> source = configuration.getSource();

        provider.validate(configuration);
        handle(provider, source);
    }

    /**
     * The main entry-point of the application.
     */
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            throw new RuntimeException("Must contain an argument for the configuration file");
        }

        String configurationFile = args[0];
        EntryPoint entryPoint = new EntryPoint(configurationFile);
        entryPoint.run();
    }

    private <T extends SourceData> void handle(Provider provider, Source<T> source)
    {
        List<T> items = source.getData(configuration.getBatchSize());
        if (items.size() > configuration.getBatchSize())
        {
            throw new RuntimeException("Source returned more data than expected");
        }
        if (items.isEmpty())
        {
            System.out.println("There is nothing to provide coordinates for, exiting...");
            return;
        }

        Map<String, List<T>> map = items.stream().collect(groupingBy(SourceData::getAddress));
        List<LookupResult> results = provider.lookup(map.keySet());
        for (LookupResult result : results)
        {
            String ip = result.getIp();
            for (T item : map.get(ip))
            {
                source.update(item, result.getLongitude(), result.getLatitude());
            }
        }
    }
}
