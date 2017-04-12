package com.antonjohansson.geolocation.source.es;

import static java.util.Collections.emptyList;

import java.math.BigDecimal;
import java.util.List;

import com.antonjohansson.geolocation.framework.Source;

/**
 * {@link Source} implementation that works against an Elasticsearch type.
 */
public class ElasticsearchSource implements Source<Document>
{
    @Override
    public List<Document> getData(int batchSize)
    {
        return emptyList();
    }

    @Override
    public void update(Document data, BigDecimal longitude, BigDecimal latitude)
    {
    }
}
