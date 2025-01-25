package com.omri.trackinglibrary.interfaces;

/**
 * LocationTracker defines the contract for tracking user locations,
 * including user creation, status updates, location updates, and retrieval of user locations.
 */
public interface LocationTracker {

    /**
     * Creates a new user with the given username and provides the result via a callback.
     *
     * @param username The username of the new user.
     * @param callback The callback to handle the response of the user creation process.
     */
    void createUser(String username, UserCallback callback);

    /**
     * Updates the active status of a user.
     *
     * @param userId   The unique identifier of the user.
     * @param isActive The new active status to set.
     * @param callback The callback to handle the response of the status update process.
     */
    void updateUserStatus(String userId, boolean isActive, UserCallback callback);

    /**
     * Gets the current status of a user.
     *
     * @param userId   The unique identifier of the user.
     * @param callback The callback to handle the response of the status retrieval process.
     */
    void getUserStatus(String userId, UserCallback callback);

    /**
     * Updates the location of a user with the given user ID, latitude, and longitude.
     * The result of the operation is returned through the provided callback.
     *
     * @param userId    The unique identifier of the user whose location is being updated.
     * @param latitude  The latitude of the new location.
     * @param longitude The longitude of the new location.
     * @param callback  The callback to handle the response of the location update process.
     */
    void updateLocation(String userId, double latitude, double longitude, LocationCallback callback);

    /**
     * Retrieves the location of a user based on their unique user ID.
     * The retrieved location is returned through the provided callback.
     *
     * @param userId   The unique identifier of the user whose location is being retrieved.
     * @param callback The callback to handle the response of the location retrieval process.
     */
    void getUserLocation(String userId, LocationCallback callback);
}