package com.omri.trackinglibrary.models;

import com.google.gson.annotations.SerializedName;

/**
 * Location represents the geographical location details of a user,
 * including the user ID, latitude, longitude, and the timestamp of the last update.
 * This class is used to track and store user locations in the tracking system.
 */
public class Location {
    /**
     * The unique identifier of the user associated with this location.
     * Serialized as "user_id" in JSON.
     */
    @SerializedName("user_id")
    private String userId;

    /**
     * The latitude coordinate of the location.
     * Valid values range from -90 to 90 degrees.
     */
    private double latitude;

    /**
     * The longitude coordinate of the location.
     * Valid values range from -180 to 180 degrees.
     */
    private double longitude;

    /**
     * The timestamp when this location was last updated.
     * Serialized as "last_updated" in JSON.
     */
    @SerializedName("last_updated")
    private String lastUpdated;

    /**
     * Constructs a new Location instance with the specified user ID, latitude, longitude, and last updated timestamp.
     * Validates all parameters upon construction.
     *
     * @param userId      The unique identifier of the user.
     * @param latitude    The latitude of the user's location (-90 to 90).
     * @param longitude   The longitude of the user's location (-180 to 180).
     * @param lastUpdated The timestamp of the last update.
     * @throws IllegalStateException if user ID is invalid
     * @throws IllegalArgumentException if coordinates are outside valid ranges
     */
    public Location(String userId, double latitude, double longitude, String lastUpdated) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastUpdated = lastUpdated;
        validate();
    }

    /**
     * Validates the location data to ensure all fields contain valid values.
     * Checks if:
     * - User ID is not null or empty
     * - Latitude is between -90 and 90 degrees
     * - Longitude is between -180 and 180 degrees
     *
     * @throws IllegalStateException if user ID is null or empty
     * @throws IllegalArgumentException if latitude or longitude are outside valid ranges
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

    /**
     * Gets the user ID associated with this location.
     * @return The unique identifier of the user
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets the latitude coordinate of this location.
     * @return The latitude value between -90 and 90 degrees
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Gets the longitude coordinate of this location.
     * @return The longitude value between -180 and 180 degrees
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Gets the timestamp when this location was last updated.
     * @return The timestamp of the last update
     */
    public String getLastUpdated() {
        return lastUpdated;
    }
}