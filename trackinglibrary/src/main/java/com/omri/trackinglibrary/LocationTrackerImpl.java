package com.omri.trackinglibrary;

import com.omri.trackinglibrary.api.ApiClient;
import com.omri.trackinglibrary.api.ApiService;
import com.omri.trackinglibrary.api.LocationUpdateRequest;
import com.omri.trackinglibrary.api.UserRequest;
import com.omri.trackinglibrary.api.UserStatusRequest;
import com.omri.trackinglibrary.interfaces.LocationCallback;
import com.omri.trackinglibrary.interfaces.LocationTracker;
import com.omri.trackinglibrary.interfaces.UserCallback;
import com.omri.trackinglibrary.models.Location;
import com.omri.trackinglibrary.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * LocationTrackerImpl is an implementation of the LocationTracker interface.
 * It handles user creation, status updates, location updates, and retrieval by interacting with the backend API.
 */
public class LocationTrackerImpl implements LocationTracker {
    private final ApiService apiService;

    /**
     * Default constructor that initializes the API service using the ApiClient.
     */
    public LocationTrackerImpl() {
        this(ApiClient.getClient().create(ApiService.class));
    }

    /**
     * Constructor that allows dependency injection of an ApiService instance.
     *
     * @param apiService The API service to be used for network requests.
     */
    public LocationTrackerImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * Creates a new user with the given username and handles the response via a callback.
     *
     * @param username The username of the new user.
     * @param callback The callback to handle the success or error response.
     */
    @Override
    public void createUser(String username, final UserCallback callback) {
        apiService.createUser(new UserRequest(username))
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            callback.onSuccess(response.body());
                        } else {
                            callback.onError("Failed to create user");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        callback.onError(t.getMessage());
                    }
                });
    }

    /**
     * Updates the active status of a user.
     *
     * @param userId   The unique identifier of the user.
     * @param isActive The new active status to set.
     * @param callback The callback to handle the success or error response.
     */
    @Override
    public void updateUserStatus(String userId, boolean isActive, UserCallback callback) {
        apiService.updateUserStatus(userId, new UserStatusRequest(isActive))
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            callback.onSuccess(response.body());
                        } else {
                            callback.onError("Failed to update user status");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        callback.onError(t.getMessage());
                    }
                });
    }

    @Override
    public void getUserStatus(String userId, UserCallback callback) {
        // We can reuse the existing getUser endpoint since it includes the isActive field
        apiService.getUser(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to get user status");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    /**
     * Updates the location of a user and handles the response via a callback.
     *
     * @param userId    The unique identifier of the user.
     * @param latitude  The latitude of the new location.
     * @param longitude The longitude of the new location.
     * @param callback  The callback to handle the success or error response.
     */
    @Override
    public void updateLocation(String userId, double latitude, double longitude, final LocationCallback callback) {
        apiService.updateLocation(new LocationUpdateRequest(userId, latitude, longitude))
                .enqueue(new Callback<Location>() {
                    @Override
                    public void onResponse(Call<Location> call, Response<Location> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            callback.onSuccess(response.body());
                        } else {
                            callback.onError("Failed to update location");
                        }
                    }

                    @Override
                    public void onFailure(Call<Location> call, Throwable t) {
                        callback.onError(t.getMessage());
                    }
                });
    }

    /**
     * Retrieves the location of a user by user ID and handles the response via a callback.
     *
     * @param userId   The unique identifier of the user.
     * @param callback The callback to handle the success or error response.
     */
    @Override
    public void getUserLocation(String userId, final LocationCallback callback) {
        apiService.getUserLocation(userId)
                .enqueue(new Callback<Location>() {
                    @Override
                    public void onResponse(Call<Location> call, Response<Location> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            callback.onSuccess(response.body());
                        } else {
                            callback.onError("Failed to get user location");
                        }
                    }

                    @Override
                    public void onFailure(Call<Location> call, Throwable t) {
                        callback.onError(t.getMessage());
                    }
                });
    }
}