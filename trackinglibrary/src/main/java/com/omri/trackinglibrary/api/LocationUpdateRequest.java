package com.omri.trackinglibrary.api;

import com.google.gson.annotations.SerializedName;

/**
 * LocationUpdateRequest represents a request to update a user's location.
 * It contains the user's ID, latitude, and longitude.
 */
public class LocationUpdateRequest {
    @SerializedName("user_id")
    private final String userId;

    private final double latitude;
    private final double longitude;

    /**
     * Constructs a new LocationUpdateRequest with the specified user ID, latitude, and longitude.
     *
     * @param userId    The unique identifier of the user.
     * @param latitude  The latitude of the user's current location.
     * @param longitude The longitude of the user's current location.
     * @throws IllegalArgumentException if any of the parameters are invalid
     */
    public LocationUpdateRequest(String userId, double latitude, double longitude) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        validate();
    }

    /**
     * Validates the location update request data.
     * @throws IllegalArgumentException if any of the parameters are invalid
     */
    private void validate() {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
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
}
