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

import com.antonjohansson.geolocation.config.Configuration;
import com.antonjohansson.geolocation.framework.Provider;
import com.antonjohansson.geolocation.framework.Source;

/**
 * Contains the main entry-point of the application.
 */
public class EntryPoint
{
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
        Configuration configuration = new Configuration(configurationFile);
        configuration.load();

        Provider provider = configuration.getProvider();
        Source<?> source = configuration.getSource();

        System.out.println(provider);
        System.out.println(source);
    }
}
