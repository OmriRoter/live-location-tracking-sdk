package com.omri.trackinglibrary;

import android.util.Log;
import com.omri.trackinglibrary.api.ApiClient;
import com.omri.trackinglibrary.api.ApiService;
import com.omri.trackinglibrary.api.LocationUpdateRequest;
import com.omri.trackinglibrary.api.UserRequest;
import com.omri.trackinglibrary.api.UserStatusRequest;
import com.omri.trackinglibrary.api.UserVerifyRequest;
import com.omri.trackinglibrary.interfaces.LocationCallback;
import com.omri.trackinglibrary.interfaces.LocationTracker;
import com.omri.trackinglibrary.interfaces.UserCallback;
import com.omri.trackinglibrary.models.Location;
import com.omri.trackinglibrary.models.User;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationTrackerImpl implements LocationTracker {
    private static final String TAG = "LocationTrackerImpl";
    private final ApiService apiService;

    public LocationTrackerImpl() {
        this(ApiClient.getClient().create(ApiService.class));
    }

    public LocationTrackerImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void verifyUser(String userId, final UserCallback callback) {
        Log.d(TAG, "Verifying user with ID: " + userId);

        UserVerifyRequest request = new UserVerifyRequest(userId);
        apiService.verifyUser(request).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
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
            public void onFailure(Call<User> call, Throwable t) {
                String error = "Network error while verifying user: " + t.getMessage();
                Log.e(TAG, error, t);
                callback.onError(error);
            }
        });
    }

    @Override
    public void createUser(String username, final UserCallback callback) {
        Log.d(TAG, "Creating user with username: " + username);

        UserRequest request = new UserRequest(username);
        apiService.createUser(request).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
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
            public void onFailure(Call<User> call, Throwable t) {
                String error = "Network error while creating user: " + t.getMessage();
                Log.e(TAG, error, t);
                callback.onError(error);
            }
        });
    }

    @Override
    public void updateUserStatus(String userId, boolean isActive, final UserCallback callback) {
        Log.d(TAG, "Updating status for userId: " + userId + " to: " + isActive);

        UserStatusRequest request = new UserStatusRequest(isActive);
        apiService.updateUserStatus(userId, request).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
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
            public void onFailure(Call<User> call, Throwable t) {
                String error = "Network error while updating status: " + t.getMessage();
                Log.e(TAG, error, t);
                callback.onError(error);
            }
        });
    }

    @Override
    public void getUserStatus(String userId, final UserCallback callback) {
        verifyUser(userId, callback);
    }

    @Override
    public void updateLocation(String userId, double latitude, double longitude, final LocationCallback callback) {
        Log.d(TAG, "Updating location - userId: " + userId + ", lat: " + latitude + ", lng: " + longitude);

        LocationUpdateRequest request = new LocationUpdateRequest(userId, latitude, longitude);
        apiService.updateLocation(request).enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
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
            public void onFailure(Call<Location> call, Throwable t) {
                String error = "Network error while updating location: " + t.getMessage();
                Log.e(TAG, error, t);
                callback.onError(error);
            }
        });
    }

    @Override
    public void getUserLocation(String userId, final LocationCallback callback) {
        Log.d(TAG, "Getting location for userId: " + userId);

        apiService.getUserLocation(userId).enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
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
            public void onFailure(Call<Location> call, Throwable t) {
                String error = "Network error while getting location: " + t.getMessage();
                Log.e(TAG, error, t);
                callback.onError(error);
            }
        });
    }

    /**
     * Extracts error message from an unsuccessful response.
     *
     * @param response The unsuccessful response
     * @return A string containing the error message and response code
     */
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