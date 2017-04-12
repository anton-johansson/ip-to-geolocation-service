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
package com.antonjohansson.geolocation.provider.ipapi;

import static java.util.Collections.emptyList;

import java.util.List;

import com.antonjohansson.geolocation.framework.Provider;
import com.antonjohansson.geolocation.framework.domain.LookupResult;

/**
 * {@link Provider} implementation that utilizes the <a>http://ip-api.com</a> API.
 */
public class IpApiProvider implements Provider
{
    private String token = "";

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    @Override
    public List<LookupResult> lookup(List<String> addresses)
    {
        return emptyList();
    }
}
