package com.omri.trackinglibrary;

import android.util.Log;
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
                    String errorBody = "";
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (IOException e) {
                        errorBody = "Could not read error body";
                    }
                    String error = "Failed to update location. Code: " + response.code() +
                            ", Error: " + errorBody;
                    Log.e(TAG, error);
                    callback.onError(error);
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
                    String error = "Failed to get location. Code: " + response.code();
                    Log.e(TAG, error);
                    callback.onError(error);
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

    @Override
    public void createUser(String username, final UserCallback callback) {
        Log.d(TAG, "Creating user with username: " + username);

        apiService.createUser(new UserRequest(username)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "User created successfully");
                    callback.onSuccess(response.body());
                } else {
                    String error = "Failed to create user. Code: " + response.code();
                    Log.e(TAG, error);
                    callback.onError(error);
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
    public void getUserStatus(String userId, final UserCallback callback) {
        Log.d(TAG, "Getting status for userId: " + userId);

        apiService.getUser(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Got user status successfully");
                    callback.onSuccess(response.body());
                } else {
                    String error = "Failed to get user status. Code: " + response.code();
                    Log.e(TAG, error);
                    callback.onError(error);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                String error = "Network error while getting user status: " + t.getMessage();
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
                    String errorBody = "";
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (IOException e) {
                        errorBody = "Could not read error body";
                    }
                    String error = "Failed to update status. Code: " + response.code() +
                            ", Error: " + errorBody;
                    Log.e(TAG, error);
                    callback.onError(error);
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
}