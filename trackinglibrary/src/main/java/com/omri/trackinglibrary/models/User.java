package com.omri.trackinglibrary.models;

/**
 * User represents a user in the system with attributes such as ID, username, and creation timestamp.
 */
public class User {
    // The unique identifier of the user
    private String id;

    // The username of the user
    private String username;

    // The timestamp indicating when the user was created
    private String createdAt;

    /**
     * Constructs a new User instance with the specified ID, username, and creation timestamp.
     *
     * @param id        The unique identifier of the user.
     * @param username  The username of the user.
     * @param createdAt The timestamp when the user was created.
     */
    public User(String id, String username, String createdAt) {
        this.id = id;
        this.username = username;
        this.createdAt = createdAt;
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
}
