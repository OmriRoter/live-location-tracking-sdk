package com.omri.trackinglibrary.api;

/**
 * UserRequest represents a request to create a new user in the system.
 * It contains the username of the user.
 */
public class UserRequest {
    // The username of the user
    private String username;

    /**
     * Constructs a new UserRequest with the specified username.
     *
     * @param username The username of the user.
     */
    public UserRequest(String username) {
        this.username = username;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username as a string.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
