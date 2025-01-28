package com.omri.trackinglibrary.models;

import com.google.gson.annotations.SerializedName;

/**
 * User represents a user in the system with attributes such as ID, username, creation timestamp, and active status.
 * This class is used to manage user data and authenticate users in the tracking system.
 */
public class User {
    /**
     * The unique identifier of the user.
     * Can be serialized from either "id" or "_id" in JSON.
     */
    @SerializedName(value = "id", alternate = {"_id"})
    private String id;

    /**
     * The username of the user.
     * Must be non-null and non-empty.
     */
    private String username;

    /**
     * The timestamp when the user was created.
     * Serialized as "created_at" in JSON.
     */
    @SerializedName("created_at")
    private String createdAt;

    /**
     * Flag indicating whether the user is currently active.
     * Serialized as "is_active" in JSON.
     */
    @SerializedName("is_active")
    private boolean isActive;

    /**
     * Constructs a new User instance with the specified ID, username, creation timestamp, and active status.
     * Validates all required fields upon construction.
     *
     * @param id        The unique identifier of the user
     * @param username  The username of the user (must be non-null and non-empty)
     * @param createdAt The timestamp when the user was created
     * @param isActive  The active status of the user (true for active, false for inactive)
     * @throws IllegalStateException if id or username are invalid
     */
    public User(String id, String username, String createdAt, boolean isActive) {
        this.id = id;
        this.username = username;
        this.createdAt = createdAt;
        this.isActive = isActive;
        validate();
    }

    /**
     * Validates the user data to ensure all required fields contain valid values.
     * Checks if:
     * - User ID is not null or empty
     * - Username is not null or empty
     *
     * @throws IllegalStateException if any required field is null or empty
     */
    public void validate() {
        if (id == null || id.isEmpty()) {
            throw new IllegalStateException("User ID cannot be null or empty");
        }
        if (username == null || username.isEmpty()) {
            throw new IllegalStateException("Username cannot be null or empty");
        }
    }

    /**
     * Gets the unique identifier of the user.
     * @return The user's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the username of the user.
     * @return The user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the timestamp when the user was created.
     * @return The creation timestamp
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Checks if the user is currently active.
     * @return true if the user is active, false otherwise
     */
    public boolean isActive() {
        return isActive;
    }
}