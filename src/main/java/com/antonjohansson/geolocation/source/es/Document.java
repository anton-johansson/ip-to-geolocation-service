package com.antonjohansson.geolocation.source.es;

import com.antonjohansson.geolocation.framework.domain.SourceData;

/**
 * Defines an Elasticsearch document.
 */
class Document implements SourceData
{
    @Override
    public String getAddress()
    {
        return "";
    }
}
