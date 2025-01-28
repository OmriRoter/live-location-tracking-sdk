package com.omri.trackinglibrary;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.omri.trackinglibrary.api.*;
import com.omri.trackinglibrary.interfaces.*;
import com.omri.trackinglibrary.models.*;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of the LocationTracker interface that provides functionality for tracking user locations
 * and managing user states through a REST API.
 * This class handles all network communications with the tracking server.
 */
public class LocationTrackerImpl implements LocationTracker {
    private static final String TAG = "LocationTrackerImpl";
    private final ApiService apiService;

    /**
     * Constructs a new LocationTrackerImpl with the default API service.
     * Uses the default ApiClient configuration.
     */
    public LocationTrackerImpl() {
        this(ApiClient.getClient().create(ApiService.class));
    }

    /**
     * Constructs a new LocationTrackerImpl with a custom API service.
     * Useful for testing and custom configurations.
     *
     * @param apiService The API service implementation to use
     */
    public LocationTrackerImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * Verifies if a user exists in the system.
     *
     * @param userId   The ID of the user to verify
     * @param callback Callback to handle the verification result
     */
    @Override
    public void verifyUser(String userId, final UserCallback callback) {
        Log.d(TAG, "Verifying user with ID: " + userId);

        UserVerifyRequest request = new UserVerifyRequest(userId);
        apiService.verifyUser(request).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "User verified successfully");
                    callback.onSuccess(response.body());
                } else {
                    String errorMessage = getErrorMessage(response);
                    Log.e(TAG, "Failed to verify user: " + errorMessage);
                    callback.onError(errorMessage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                String error = "Network error while verifying user: " + t.getMessage();
                Log.e(TAG, error, t);
                callback.onError(error);
            }
        });
    }

    /**
     * Creates a new user in the system.
     *
     * @param username The username for the new user
     * @param callback Callback to handle the user creation result
     */
    @Override
    public void createUser(String username, final UserCallback callback) {
        Log.d(TAG, "Creating user with username: " + username);

        UserRequest request = new UserRequest(username);
        apiService.createUser(request).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "User created successfully");
                    callback.onSuccess(response.body());
                } else {
                    String errorMessage = getErrorMessage(response);
                    Log.e(TAG, "Failed to create user: " + errorMessage);
                    callback.onError(errorMessage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                String error = "Network error while creating user: " + t.getMessage();
                Log.e(TAG, error, t);
                callback.onError(error);
            }
        });
    }

    /**
     * Updates the active status of a user.
     *
     * @param userId   The ID of the user to update
     * @param isActive The new active status to set
     * @param callback Callback to handle the status update result
     */
    @Override
    public void updateUserStatus(String userId, boolean isActive, final UserCallback callback) {
        Log.d(TAG, "Updating status for userId: " + userId + " to: " + isActive);

        UserStatusRequest request = new UserStatusRequest(isActive);
        apiService.updateUserStatus(userId, request).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Status updated successfully");
                    callback.onSuccess(response.body());
                } else {
                    String errorMessage = getErrorMessage(response);
                    Log.e(TAG, "Failed to update status: " + errorMessage);
                    callback.onError(errorMessage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                String error = "Network error while updating status: " + t.getMessage();
                Log.e(TAG, error, t);
                callback.onError(error);
            }
        });
    }

    /**
     * Gets the current status of a user.
     * This method internally uses verifyUser since the server doesn't provide a dedicated status endpoint.
     *
     * @param userId   The ID of the user to check
     * @param callback Callback to handle the status retrieval result
     */
    @Override
    public void getUserStatus(String userId, final UserCallback callback) {
        verifyUser(userId, callback);
    }

    /**
     * Updates the location of a user.
     *
     * @param userId    The ID of the user whose location is being updated
     * @param latitude  The new latitude coordinate
     * @param longitude The new longitude coordinate
     * @param callback  Callback to handle the location update result
     */
    @Override
    public void updateLocation(String userId, double latitude, double longitude, final LocationCallback callback) {
        Log.d(TAG, "Updating location - userId: " + userId + ", lat: " + latitude + ", lng: " + longitude);

        LocationUpdateRequest request = new LocationUpdateRequest(userId, latitude, longitude);
        apiService.updateLocation(request).enqueue(new Callback<Location>() {
            @Override
            public void onResponse(@NonNull Call<Location> call, @NonNull Response<Location> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Location update successful");
                    callback.onSuccess(response.body());
                } else {
                    String errorMessage = getErrorMessage(response);
                    Log.e(TAG, "Failed to update location: " + errorMessage);
                    callback.onError(errorMessage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Location> call, @NonNull Throwable t) {
                String error = "Network error while updating location: " + t.getMessage();
                Log.e(TAG, error, t);
                callback.onError(error);
            }
        });
    }

    /**
     * Gets the current location of a user.
     *
     * @param userId   The ID of the user whose location to retrieve
     * @param callback Callback to handle the location retrieval result
     */
    @Override
    public void getUserLocation(String userId, final LocationCallback callback) {
        Log.d(TAG, "Getting location for userId: " + userId);

        apiService.getUserLocation(userId).enqueue(new Callback<Location>() {
            @Override
            public void onResponse(@NonNull Call<Location> call, @NonNull Response<Location> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Got location successfully");
                    callback.onSuccess(response.body());
                } else {
                    String errorMessage = getErrorMessage(response);
                    Log.e(TAG, "Failed to get location: " + errorMessage);
                    callback.onError(errorMessage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Location> call, @NonNull Throwable t) {
                String error = "Network error while getting location: " + t.getMessage();
                Log.e(TAG, error, t);
                callback.onError(error);
            }
        });
    }

    /**
     * Extracts error message from an unsuccessful response.
     * Attempts to read the error body and formats it with the response code.
     *
     * @param response The unsuccessful response to extract the error from
     * @return A formatted string containing the error message and response code
     */
    @SuppressLint("DefaultLocale")
    private String getErrorMessage(Response<?> response) {
        String errorBody = "";
        try {
            if (response.errorBody() != null) {
                errorBody = response.errorBody().string();
            }
        } catch (IOException e) {
            errorBody = "Could not read error body";
        }
        return String.format("Code: %d, Error: %s", response.code(), errorBody);
    }
}