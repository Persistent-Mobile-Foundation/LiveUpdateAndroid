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

/**
 * UserProfileListener interface
 * Listener for get/setUserProfile API
 *
 * @see com.ibm.mfp.LiveUpdateManager
 */
public interface UserProfileListener {
    /***
     * This method is called when fetching user profile from the server was successful
     *
     * @param profile - the user profile from the server
     * @see UserProfile
     */
    void onSuccess(UserProfile profile);

    /***
     * This method is called when get user profile from the server was unsuccessful
     *
     * @param wlFailResponse - the failure response object
     * @see  WLFailResponse
     */
    void onFailure(WLFailResponse wlFailResponse);
}
