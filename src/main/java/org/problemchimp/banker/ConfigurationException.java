package org.problemchimp.banker;

/**
 * Thrown when a required configuration value is missing.
 * @author Heather McCartney
 *
 */
public class ConfigurationException extends RuntimeException {

    private static final long serialVersionUID = 1859940744711734077L;

    public ConfigurationException() {
        super();
    }

    public ConfigurationException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(Throwable cause) {
        super(cause);
    }

}
