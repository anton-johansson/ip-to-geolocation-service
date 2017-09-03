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
package com.antonjohansson.geolocation.provider.ipapi.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines the result of a lookup using the IP API.
 */
public class LookupResponse
{
    private @JsonProperty("query") String address = "";
    private @JsonProperty("lat") BigDecimal latitude = BigDecimal.ZERO;
    private @JsonProperty("lon") BigDecimal longitude = BigDecimal.ZERO;

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public BigDecimal getLatitude()
    {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude)
    {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude()
    {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude)
    {
        this.longitude = longitude;
    }
}
