package com.antonjohansson.geolocation.framework.domain;

import java.math.BigDecimal;

/**
 * Defines a lookup result.
 */
public class LookupResult
{
    private String ip = "";
    private BigDecimal longitude = BigDecimal.ZERO;
    private BigDecimal latitude = BigDecimal.ZERO;

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public BigDecimal getLongitude()
    {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude)
    {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude()
    {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude)
    {
        this.latitude = latitude;
    }
}
