package com.omri.trackinglibrary.models;

/**
 * User represents a user in the system with attributes such as ID, username, creation timestamp, and active status.
 */
public class User {
    // The unique identifier of the user
    private String id;

    // The username of the user
    private String username;

    // The timestamp indicating when the user was created
    private String createdAt;

    // The active status of the user
    private boolean isActive;

    /**
     * Constructs a new User instance with the specified ID, username, creation timestamp, and active status.
     *
     * @param id        The unique identifier of the user.
     * @param username  The username of the user.
     * @param createdAt The timestamp when the user was created.
     * @param isActive  The active status of the user.
     */
    public User(String id, String username, String createdAt, boolean isActive) {
        this.id = id;
        this.username = username;
        this.createdAt = createdAt;
        this.isActive = isActive;
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return The user ID as a string.
     */
    public String getId() {
        return id;
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
     * Gets the timestamp indicating when the user was created.
     *
     * @return The creation timestamp as a string.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the active status of the user.
     *
     * @return The active status as a boolean.
     */
    public boolean isActive() {
        return isActive;
    }
}