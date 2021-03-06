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
package com.antonjohansson.geolocation.source.es;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines a request to update a document.
 *
 * @param <T> The type of the document.
 */
public class UpdateDocumentRequest<T>
{
    private @JsonProperty("doc") T document;

    public T getDocument()
    {
        return document;
    }

    public void setDocument(T document)
    {
        this.document = document;
    }

    /**
     * The actual coordinates to update.
     */
    public static class UpdateDocumentLocation
    {
        private @JsonProperty("lon") BigDecimal longitude;
        private @JsonProperty("lat") BigDecimal latitude;

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
}
