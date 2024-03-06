/**
 * © Copyright 2016 IBM Corp.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm.mfp;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.List;
import java.util.Map;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLResourceRequest;
import com.worklight.wlclient.api.WLResponse;
import com.worklight.wlclient.api.WLResponseListener;


public class UserProfileManager {

    private final static String CONFIGURATION_SCOPE = "configuration-user-login";
    private final static String PROFILE_SERVICE_URL = "adapters/liveUpdateAdapter/v1/userProfile";
    private static URI userProfileURL = URI.create(PROFILE_SERVICE_URL);

    /**
     * @param keysToFetch - optional (can be null). if provided,will query only those selected keys inside the user's profile
     *                   otherwise: will fetch the whole user profile
     */
    public static void getUserProfile(List<String> keysToFetch, UserProfileListener callback) {
        sendGetRequest(WLResourceRequest.GET, keysToFetch, callback);
        // sends a GET WL Resource request to the server
    }

    /**
     * updates the server's user profile
     *
     * @param properties - will upload the properties to the server.
     *                   the format is key=value map. if the key already exists in the user's profile
     *                   an override action will be performed.

     */
    public static void setUserProfile(Map<String, String> properties, UserProfileListener callback)  {
        // sends a POST WL Resource request to the server
        sendSetRequest(properties, callback);
    }


    /**
     * deletes a user profile from the server
     *
     * @param keysToDelete - if provided (can be null), will selectively delete from the user's profile content.
     * @return
     */
    public static void deleteUserProfile(List<String> keysToDelete, UserProfileListener callback) {
        // sends a DELETE WL Resource request to the server
        sendGetRequest(WLResourceRequest.DELETE, keysToDelete, callback);
    }

    static void sendGetRequest(String httpMethodType, List<String> params, final UserProfileListener userProfileListener) {
        String MN = "sendSetRequest";
        WLResourceRequest request = new WLResourceRequest(userProfileURL, httpMethodType, CONFIGURATION_SCOPE);

        Log.d(MN , ":  userProfileURL = " + userProfileURL + " params = " + params);

        if (params != null) {
            StringBuffer buffer = new StringBuffer();
            for (String paramKey : params) {
                buffer.append(paramKey).append(','); // what about the last element?
            }
            request.setQueryParameter("keys", buffer.toString());
        }

        request.send(new MyResponseListener(userProfileListener,MN));
    }

    static void sendSetRequest(Map<String, String> params, final UserProfileListener userProfileListener) {
        String MN = "sendSetRequest";
        try {
            WLResourceRequest request = new WLResourceRequest(userProfileURL, WLResourceRequest.POST, CONFIGURATION_SCOPE);

            Log.d(MN, ":  userProfileURL = " + userProfileURL + " params = " + params);

            JSONObject body = new JSONObject(); // bare in mind that if calling from JS this is double conversion
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    body.put(entry.getKey(), entry.getValue());
                }
            }

            request.send(body, new MyResponseListener(userProfileListener, MN));
        } catch (JSONException ex) {
             throw new IllegalArgumentException(ex); //this should never happen
        }
    }


    static class MyResponseListener implements WLResponseListener {
        private final UserProfileListener userProfileListener;
        private final String MN;

        public MyResponseListener(final UserProfileListener listener,final String parentMethodName) {
            userProfileListener = listener;
            MN=parentMethodName;
        }

        @Override
        public void onSuccess(WLResponse wlResponse) {
            JSONObject json = new JSONObject();
            // set operation may return 200OK with empty JSON.

            if (wlResponse.getResponseJSON() != null) {
                json = wlResponse.getResponseJSON();
            }
            //next line may fail since I don't know yet
            // the exact server response structure...
            UserProfile profile = new UserProfile(json);
            userProfileListener.onSuccess(profile);
        }

        @Override
        public void onFailure(WLFailResponse wlFailResponse) {
            Log.e(MN , ": error while accessing profile from server. error = " + wlFailResponse.getErrorMsg());
            userProfileListener.onFailure(wlFailResponse);
        }
    }
}
