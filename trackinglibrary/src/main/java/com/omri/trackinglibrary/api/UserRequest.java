package com.omri.trackinglibrary.api;

/**
 * UserRequest represents a request to create a new user in the system.
 * It contains the username of the user.
 */
public class UserRequest {
    private String username;

    /**
     * Constructs a new UserRequest with the specified username.
     *
     * @param username The username of the user.
     * @throws IllegalArgumentException if the username is invalid
     */
    public UserRequest(String username) {
        this.username = username;
        validate();
    }

    /**
     * Validates the username.
     * @throws IllegalArgumentException if the username is invalid
     */
    private void validate() {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (username.length() < 3) {
            throw new IllegalArgumentException("Username must be at least 3 characters long");
        }
        if (username.length() > 50) {
            throw new IllegalArgumentException("Username cannot be longer than 50 characters");
        }
    }

    /**
     * Gets the username of the user.
     * @return The username as a String
     */
    public String getUsername() {
        return username;
    }
}
