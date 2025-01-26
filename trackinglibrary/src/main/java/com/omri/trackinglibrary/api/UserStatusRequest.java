package com.omri.trackinglibrary.api;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a request to update a user's active status.
 */
public class UserStatusRequest {
    @SerializedName("is_active")
    private boolean isActive;

    /**
     * Constructs a new UserStatusRequest.
     *
     * @param isActive The new active status to set
     */
    public UserStatusRequest(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Gets the active status.
     *
     * @return The active status as a boolean
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets the active status.
     *
     * @param active The active status to set
     */
    public void setActive(boolean active) {
        isActive = active;
    }
}