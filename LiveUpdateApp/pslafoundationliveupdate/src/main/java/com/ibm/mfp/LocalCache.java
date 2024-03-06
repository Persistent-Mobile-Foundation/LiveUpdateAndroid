package com.ibm.mfp;

public class LocalCache {

    public synchronized static void saveConfiguration(Configuration configuration) {
        CacheFileManager.save (configuration);
    }

    public synchronized static Configuration getConfiguration(String configurationId) {
        return CacheFileManager.isExpired(configurationId) ? null : CacheFileManager.configuration(configurationId);
    }
}