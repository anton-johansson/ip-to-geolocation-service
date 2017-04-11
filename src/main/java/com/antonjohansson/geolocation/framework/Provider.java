package com.antonjohansson.geolocation.framework;

import java.util.List;

import com.antonjohansson.geolocation.framework.domain.LookupResult;

/**
 * Provides geo-locations for IP addresses.
 */
public interface Provider
{
    /**
     * Performs a lookup for a given set of addresses.
     *
     * @param addresses The list of addresses to look up.
     * @return Returns the lookup results.
     */
    List<LookupResult> lookup(List<String> addresses);
}
