/**
 *   © Copyright 2016 IBM Corp.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.ibm.mfp;

import com.worklight.common.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Configuration class
 *
 * The class provides API for an obtained configuration.
 * Contains API for getting value for a property.
 */
public class Configuration {
    private static final Logger logger = Logger.getInstance(Configuration.class.getName());

    public static final String PROPERTIES_KEY = "properties";

    private JSONObject data;
    private String id;

    public JSONObject getData() {
        return data;
    }

    public String getId() {
        return id;
    }

    public Configuration(String id, JSONObject data) {
        this.id = id;
        this.data = data;
    }


    public String getProperty(String propertyId) {
        String property = null;
        try {
            property =  this.data.getJSONObject(PROPERTIES_KEY).getString(propertyId);
        } catch (JSONException e) {
            logger.error("getProperty: Cannot get property " + propertyId);
        }
        return property;
    }

    @Override
    public String toString() {
        return "ConfigurationInstance{" +
                "data=" + data +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Configuration that = (Configuration) o;

        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = data != null ? data.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
