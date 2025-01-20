package com.omri.trackinglibrary.api;

/**
 * LocationUpdateRequest represents a request to update a user's location.
 * It contains the user's ID, latitude, and longitude.
 */
public class LocationUpdateRequest {
    // The unique identifier of the user
    private String userId;

    // The latitude of the user's current location
    private double latitude;

    // The longitude of the user's current location
    private double longitude;

    /**
     * Constructs a new LocationUpdateRequest with the specified user ID, latitude, and longitude.
     *
     * @param userId   The unique identifier of the user.
     * @param latitude The latitude of the user's current location.
     * @param longitude The longitude of the user's current location.
     */
    public LocationUpdateRequest(String userId, double latitude, double longitude) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Gets the user ID.
     *
     * @return The user ID as a string.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId The user ID to set.
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
     * Sets the latitude of the user's location.
     *
     * @param latitude The latitude to set.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
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
     * Sets the longitude of the user's location.
     *
     * @param longitude The longitude to set.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
