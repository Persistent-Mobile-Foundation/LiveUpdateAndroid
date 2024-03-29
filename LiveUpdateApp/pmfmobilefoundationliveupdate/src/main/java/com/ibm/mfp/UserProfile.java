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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.logging.Logger;

public class UserProfile {

    private JSONObject properties;

    public UserProfile(JSONObject params) {
        properties = params;
    }

    public UserProfile(Map<String,String> params) throws JSONException {
        if (params != null && params.size() > 0) {
            for (String paramKey : params.keySet()) {
                properties.put(paramKey,params.get(paramKey));
            }
        }
    }


    public JSONObject toJSON() {
        return properties;
    }
}