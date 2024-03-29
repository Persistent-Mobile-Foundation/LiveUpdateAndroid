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
public interface Configuration {
    /**
     * Check if a feature is enabled
     * @param featureId - the feature id to be checked
     * @return true if feature is enabled or null for non existing feature.
     */
    public Boolean isFeatureEnabled (String featureId);


    /**
     * Get value of a property
     * @param propertyId -  the property id
     * @return the value for the given propertyId, or null in case the property doesn't exist
     */
    public String getProperty (String propertyId);
}

