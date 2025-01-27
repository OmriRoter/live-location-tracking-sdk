package com.omri.trackinglibrary.models;

import com.google.gson.annotations.SerializedName;

/**
 * User represents a user in the system with attributes such as ID, username, creation timestamp, and active status.
 */
public class User {
    @SerializedName(value = "id", alternate = {"_id"})
    private String id;

    private String username;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("is_active")
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
        validate();
    }

    /**
     * Validates the user data.
     * @throws IllegalStateException if required fields are invalid
     */
    public void validate() {
        if (id == null || id.isEmpty()) {
            throw new IllegalStateException("User ID cannot be null or empty");
        }
        if (username == null || username.isEmpty()) {
            throw new IllegalStateException("Username cannot be null or empty");
        }
    }

    public String getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public boolean isActive() {
        return isActive;
    }
}
