package com.omri.trackinglibrary.interfaces;


/**
 * LocationTracker defines the contract for tracking user locations,
 * including user creation, verification, status updates, location updates, and retrieval of user locations.
 */
public interface LocationTracker {

    void createUser(String username, UserCallback callback);
    void verifyUser(String userId, UserCallback callback);
    void updateUserStatus(String userId, boolean isActive, UserCallback callback);
    void getUserStatus(String userId, UserCallback callback);

    void updateLocation(String userId, double latitude, double longitude, LocationCallback callback);
    void getUserLocation(String userId, LocationCallback callback);
}
