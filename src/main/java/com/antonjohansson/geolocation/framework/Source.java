package com.antonjohansson.geolocation.framework;

import java.math.BigDecimal;
import java.util.List;

import com.antonjohansson.geolocation.framework.domain.SourceData;

/**
 * Defines a source from where to get IP addresses and update geo-location.
 *
 * @param <T> The type of the data that this source handles.
 */
public interface Source<T extends SourceData>
{
    /**
     * Get data to provide with geo-location data.
     *
     * @param batchSize The number of sources to provide.
     * @return Returns the list of data.
     */
    List<T> getData(int batchSize);

    /**
     * Updates a given item with geo-location data.
     *
     * @param data The data that was fetched.
     * @param longitude The longitude coordinate.
     * @param latitude The latitude coordinate.
     */
    void update(T data, BigDecimal longitude, BigDecimal latitude);
}
