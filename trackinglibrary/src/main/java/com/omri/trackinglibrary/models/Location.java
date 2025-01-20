package com.omri.trackinglibrary.models;

/**
 * Location represents the geographical location details of a user,
 * including the user ID, latitude, longitude, and the timestamp of the last update.
 */
public class Location {
    // The unique identifier of the user associated with this location
    private String userId;

    // The latitude coordinate of the user's location
    private double latitude;

    // The longitude coordinate of the user's location
    private double longitude;

    // The timestamp of when the location was last updated
    private String lastUpdated;

    /**
     * Constructs a new Location instance with the specified user ID, latitude, longitude, and last updated timestamp.
     *
     * @param userId      The unique identifier of the user.
     * @param latitude    The latitude of the user's location.
     * @param longitude   The longitude of the user's location.
     * @param lastUpdated The timestamp of the last update.
     */
    public Location(String userId, double latitude, double longitude, String lastUpdated) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastUpdated = lastUpdated;
    }

    /**
     * Gets the user ID associated with the location.
     *
     * @return The user ID as a string.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets the latitude of the user's location.
     *
     * @return The latitude as a double.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Gets the longitude of the user's location.
     *
     * @return The longitude as a double.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Gets the timestamp of the last location update.
     *
     * @return The last updated timestamp as a string.
     */
    public String getLastUpdated() {
        return lastUpdated;
    }
}
