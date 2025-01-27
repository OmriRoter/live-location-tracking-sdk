package com.omri.trackinglibrary.models;

import com.google.gson.annotations.SerializedName;

/**
 * User represents a user in the system with attributes such as ID, username, creation timestamp, and active status.
 */
public class User {

    /**
     * Internal class to handle MongoDB ObjectId structure
     */
    public static class MongoId {
        @SerializedName("$oid")
        private String oid;

        public String getOid() {
            return oid;
        }
    }

    @SerializedName("_id")
    private MongoId id;

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
        MongoId mongoId = new MongoId();
        // When creating a new User object manually, we store the plain ID in the oid field
        if (id != null) {
            mongoId.oid = id;
        }
        this.id = mongoId;
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
        if (getId() == null || getId().isEmpty()) {
            throw new IllegalStateException("User ID cannot be null or empty");
        }
        if (username == null || username.isEmpty()) {
            throw new IllegalStateException("Username cannot be null or empty");
        }
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return The user ID as a string.
     */
    public String getId() {
        return id != null ? id.getOid() : null;
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