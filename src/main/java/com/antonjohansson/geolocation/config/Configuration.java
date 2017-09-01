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

import static java.util.stream.Collectors.toList;
import static org.apache.commons.beanutils.PropertyUtils.isWriteable;
import static org.apache.commons.beanutils.PropertyUtils.setProperty;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Properties;

import com.antonjohansson.geolocation.framework.Provider;
import com.antonjohansson.geolocation.framework.Source;

/**
 * Holds the configuration of the application.
 */
public class Configuration
{
    private final String configurationFile;
    private Provider provider;
    private Source<?> source;

    public Configuration(String configurationFile)
    {
        this.configurationFile = configurationFile;
    }

    /**
     * Loads the configuration from the file.
     */
    public void load()
    {
        Properties properties = getProperties(configurationFile);
        this.provider = getInstance(properties, Provider.class);
        this.source = getInstance(properties, Source.class);
    }

    private <T> T getInstance(Properties properties, Class<T> target)
    {
        String type = target.getSimpleName().toLowerCase();
        String className = properties.getProperty(type);
        Class<?> clazz = getClass(className);
        if (!target.isAssignableFrom(clazz))
        {
            throw new RuntimeException("'" + clazz.getName() + "' is not a valid " + type);
        }
        T instance = newInstance(clazz.asSubclass(target));

        List<String> keys = properties.keySet()
                .stream()
                .filter(key -> key instanceof String)
                .map(key -> (String) key)
                .filter(key -> key.startsWith(type))
                .filter(key -> key.length() > type.length())
                .map(key -> key.substring(type.length() + 1))
                .filter(key -> isWriteable(instance, key))
                .collect(toList());

        for (String key : keys)
        {
            Object value = properties.get(type + "." + key);
            try
            {
                setProperty(instance, key, value);
            }
            catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
            {
                throw new RuntimeException(e);
            }
        }

        return instance;
    }

    private Class<?> getClass(String className)
    {
        try
        {
            return Class.forName(className);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException("Could not load class '" + className + "'.");
        }
    }

    private <T> T newInstance(Class<T> clazz)
    {
        try
        {
            return clazz.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
    }

    private Properties getProperties(String configurationFile)
    {
        try (InputStream stream = new FileInputStream(configurationFile))
        {
            Properties properties = new Properties();
            properties.load(stream);
            return properties;
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException("Configuration file does not exist");
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the provider to use.
     */
    public Provider getProvider()
    {
        return provider;
    }

    /**
     * Gets the source to use.
     */
    public Source<?> getSource()
    {
        return source;
    }
}
