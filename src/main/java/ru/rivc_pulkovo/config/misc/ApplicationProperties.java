package ru.rivc_pulkovo.config.misc;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to the application.
 *
 * <p> Properties are configured in the application.yml file. </p>
 * <p> This class also load properties in the Spring Environment from the git.properties and META-INF/build-info.properties
 * files if they are found in the classpath.</p>
 */
@ConfigurationProperties(prefix = "department", ignoreUnknownFields = false)
public class ApplicationProperties {
    private final Cache cache = new Cache();

    /**
     * <p>Getter for the field <code>cache</code>.</p>
     *
     * @return a {@link ApplicationProperties.Cache} object.
     */
    public Cache getCache() {
        return cache;
    }

    public static class Cache {

        private final Hazelcast hazelcast = new Hazelcast();

        public Hazelcast getHazelcast() {
            return hazelcast;
        }

        public static class Hazelcast {

            private int timeToLiveSeconds = ApplicationDefaults.Cache.Hazelcast.timeToLiveSeconds;

            private int backupCount = ApplicationDefaults.Cache.Hazelcast.backupCount;

            public int getTimeToLiveSeconds() {
                return timeToLiveSeconds;
            }

            public void setTimeToLiveSeconds(int timeToLiveSeconds) {
                this.timeToLiveSeconds = timeToLiveSeconds;
            }

            public int getBackupCount() {
                return backupCount;
            }

            public void setBackupCount(int backupCount) {
                this.backupCount = backupCount;
            }
        }
    }
}
