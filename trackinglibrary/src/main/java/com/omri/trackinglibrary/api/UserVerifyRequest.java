package com.omri.trackinglibrary.api;

import com.google.gson.annotations.SerializedName;

/**
 * UserVerifyRequest represents a request to verify a user's existence in the system.
 * It contains the user's ID that needs to be verified.
 */
public class UserVerifyRequest {
    @SerializedName("user_id")
    private String userId;

    /**
     * Constructs a new UserVerifyRequest with the specified user ID.
     *
     * @param userId The unique identifier of the user to verify.
     */
    public UserVerifyRequest(String userId) {
        this.userId = userId;
        validate();
    }

    /**
     * Validates the user ID.
     * @throws IllegalArgumentException if the user ID is invalid
     */
    private void validate() {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
    }

    public String getUserId() {
        return userId;
    }
}
