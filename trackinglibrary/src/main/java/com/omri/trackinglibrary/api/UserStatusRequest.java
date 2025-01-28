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

    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
}
