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


import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLResourceRequest;
import com.worklight.wlclient.api.WLResponse;
import com.worklight.wlclient.api.WLResponseListener;
import android.util.DisplayMetrics;
import org.json.JSONObject;
import android.os.Build;
import android.util.Log;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

/**
 * LiveUpdateManager class
 *
 * A manager class for the  LiveUpdate APIs
 */

public class LiveUpdateManager {

    private final static String CONFIGURATION_SCOPE = "configuration-user-login";
    private final static String SERVICE_URL = "adapters/liveUpdateAdapter/v1/configuration";
    private final static String FIRMWARE = "X-firmware";
    private final static String MODEL = "X-model";
    private final static String SCREEN = "X-screen";
    private final static String DATE = "Date";
    // according to http://tools.ietf.org/html/rfc7231#section-7.1.1.1 and RFC 1123
    private static SimpleDateFormat RFC_1123_DATE_FORMATTER = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");

    /**
     * getConfiguration - obtains a configuration from server / cache by params
     * The cache is enabled for this API
     *
     * @param params - the params used by the server to return a configuration.
     * @param configurationListener - the configuration listener for receiving the configuration
     */
    public static void getConfiguration(Map<String,String> params, ConfigurationListener configurationListener) {
        getConfiguration(params, true, configurationListener);
    }

    /**
     * getConfiguration - obtains a configuration from server / cache by params
     * @param params - the params used by the server to return a configuration
     * @param useCache - true to use cache, false to always obtain configuration from server
     * @param configurationListener - the configuration listener for receiving the configuration
     */
    public static void getConfiguration(Map<String,String> params, boolean useCache, ConfigurationListener configurationListener) {
        URI url = URI.create(SERVICE_URL);
        String id = buildIDFromParams(params);

        Log.d("getConfiguration", "params = " + params + ", useCache = " + useCache + ", url = " + url);
        getConfiguration(id, url, params, useCache, configurationListener);
    }


    private static void getConfiguration(String id, URI url, Map<String, String> params, boolean useCache, final ConfigurationListener configurationListener) {
            sendConfigRequest(id, url, params, configurationListener);
    }


    private static void sendConfigRequest(final String id, URI url, Map<String, String> params, final ConfigurationListener configurationListener) {
        WLResourceRequest configurationServiceRequest = new WLResourceRequest(url, WLResourceRequest.GET, CONFIGURATION_SCOPE);

        Log.d("sendConfigRequest"," id = " + id + ", url = " + url + "params = " + params);

        if (params != null) {
            for (String paramKey : params.keySet()) {
                configurationServiceRequest.setQueryParameter(paramKey, params.get(paramKey));
            }
          }
        configurationServiceRequest.addHeader(FIRMWARE, System.getProperty("os.version"));
        configurationServiceRequest.addHeader(SCREEN, Double.toString(getScreenResolution()));
        configurationServiceRequest.addHeader(MODEL ,getModelName());

        // add http date header according to RFC 1123

        Calendar calendar = Calendar.getInstance();
        String currentDate = RFC_1123_DATE_FORMATTER.format(calendar.getTime());
        configurationServiceRequest.addHeader(DATE,currentDate);

        configurationServiceRequest.send(new WLResponseListener() {
            @Override
            public void onSuccess(WLResponse wlResponse) {
                JSONObject json = wlResponse.getResponseJSON();

                if (json == null) {
                    Log.e("sendConfigRequest"," invalid JSON response");
                    json = new JSONObject();
                }
                Configuration configuration = new Configuration(id, json);
                configurationListener.onSuccess(configuration);
            }

            @Override
            public void onFailure(WLFailResponse wlFailResponse) {
                Log.e("sendConfigRequest"," error while retriving configuration from server. error = " + wlFailResponse.getErrorMsg());
                configurationListener.onFailure(wlFailResponse);
            }
        });
    }

    private static String buildIDFromParams(Map<String, String> params) {
        Log.d("buildIDFromParams"," params = " + params);
        String paramsId = "";
        if (params != null && params.size() > 0) {
            for (String paramKey : params.keySet()) {
                paramsId += "_" + paramKey + "" + params.get(paramKey);
            }
        }
        Log.d("buildIDFromParams"," paramsId = " + paramsId);
        return paramsId;
    }

   static double getScreenResolution()
   {
       DisplayMetrics metrics = new DisplayMetrics();
       int width = metrics.widthPixels;
       int height = metrics.heightPixels;
       int dpi = metrics.densityDpi;
       double widthInches=(double)width/(double)dpi;
       double heightInInches=(double)height/(double)dpi;
       double diagonal = Math.sqrt(widthInches*widthInches+heightInInches*heightInInches);
       return diagonal;
   }

   private static String getModelName() {
     String manufacturer = Build.MANUFACTURER;
     String brand = Build.BRAND; // for getting BrandName
     String model = Build.MODEL; // for getting Model of the device
     return manufacturer+" "+brand+" "+model;
   }

    public enum Constants {
        LATITUDE("latitude"),
        LONGITUDE("longitude"),
        ALTITUDE("altitude");
        final public String header ;
        Constants(String hdr)
        {
            this.header = hdr;
        }
    }
}
