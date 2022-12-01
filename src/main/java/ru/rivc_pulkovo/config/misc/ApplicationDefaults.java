package ru.rivc_pulkovo.config.misc;

/**
 * <p>ApplicationDefaults interface.</p>
 */
public interface ApplicationDefaults {
    interface Cache {

        interface Hazelcast {

            int timeToLiveSeconds = 3600; // 1 hour
            int backupCount = 1;

            interface ManagementCenter {

                boolean enabled = false;
                int updateInterval = 3;
                String url = "";
            }
        }
    }
}
