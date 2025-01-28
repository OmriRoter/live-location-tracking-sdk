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
     * Checks if the user is currently active.
     * @return true if the user is active, false otherwise
     */
    public boolean isActive() {
        return isActive;
    }
    /**
     * Sets the active status of the user.
     *
     * @param active The new active status to set (true for active, false for inactive)
     */
    public void setActive(boolean active) {
        isActive = active;
    }
}
