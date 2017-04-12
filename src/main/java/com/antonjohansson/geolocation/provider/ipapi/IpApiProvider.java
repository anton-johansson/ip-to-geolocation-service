package com.antonjohansson.geolocation.provider.ipapi;

import static java.util.Collections.emptyList;

import java.util.List;

import com.antonjohansson.geolocation.framework.Provider;
import com.antonjohansson.geolocation.framework.domain.LookupResult;

/**
 * {@link Provider} implementation that utilizes the {@linkplain http://ip-api.com} API.
 */
public class IpApiProvider implements Provider
{
    @Override
    public List<LookupResult> lookup(List<String> addresses)
    {
        return emptyList();
    }
}
