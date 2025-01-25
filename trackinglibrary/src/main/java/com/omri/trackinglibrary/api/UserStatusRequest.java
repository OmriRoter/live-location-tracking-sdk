package com.omri.trackinglibrary.api;

/**
 * UserStatusRequest represents a request to update a user's active status.
 */
public class UserStatusRequest {
    // The active status to be set
    private boolean isActive;

    /**
     * Constructs a new UserStatusRequest with the specified active status.
     *
     * @param isActive The active status to set.
     */
    public UserStatusRequest(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Gets the active status.
     *
     * @return The active status as a boolean.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets the active status.
     *
     * @param active The active status to set.
     */
    public void setActive(boolean active) {
        isActive = active;
    }
}