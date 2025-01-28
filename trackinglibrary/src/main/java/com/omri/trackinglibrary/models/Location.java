package com.omri.trackinglibrary.models;

import com.google.gson.annotations.SerializedName;

/**
 * Location represents the geographical location details of a user,
 * including the user ID, latitude, longitude, and the timestamp of the last update.
 */
public class Location {
    @SerializedName("user_id")
    private String userId;

    private double latitude;
    private double longitude;

    @SerializedName("last_updated")
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
        validate();
    }

    /**
     * Validates the location data.
     * @throws IllegalStateException if user ID is invalid
     * @throws IllegalArgumentException if coordinates are invalid
     */
    public void validate() {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalStateException("User ID cannot be null or empty");
        }
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Invalid latitude value");
        }
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Invalid longitude value");
        }
    }

    public String getUserId() {
        return userId;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public String getLastUpdated() {
        return lastUpdated;
    }
}
