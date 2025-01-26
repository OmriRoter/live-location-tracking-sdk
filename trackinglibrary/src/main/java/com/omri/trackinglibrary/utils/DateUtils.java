package com.omri.trackinglibrary.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Utility class for handling date and time operations.
 */
public class DateUtils {
    private static final SimpleDateFormat ISO_FORMAT;

    static {
        ISO_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        ISO_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Formats a MongoDB timestamp to ISO 8601 format.
     *
     * @param timestamp The MongoDB timestamp in milliseconds
     * @return Formatted date string in ISO 8601 format
     */
    public static String formatMongoDate(long timestamp) {
        return ISO_FORMAT.format(new Date(timestamp));
    }

    /**
     * Gets the current time in ISO 8601 format.
     *
     * @return Current time string in ISO 8601 format
     */
    public static String getCurrentISODate() {
        return ISO_FORMAT.format(new Date());
    }
}