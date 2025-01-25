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

public class LocationTrackerImpl implements LocationTracker {
    private final ApiService apiService;

    public LocationTrackerImpl() {
        this(ApiClient.getClient().create(ApiService.class));
    }

    public LocationTrackerImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void createUser(String username, final UserCallback callback) {
        apiService.createUser(new UserRequest(username))
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            callback.onSuccess(response.body());
                        } else {
                            callback.onError(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        callback.onError(t.getMessage());
                    }
                });
    }

    @Override
    public void updateUserStatus(String userId, boolean isActive, final UserCallback callback) {
        UserStatusRequest request = new UserStatusRequest(isActive);
        apiService.updateUserStatus(userId, request)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            callback.onSuccess(response.body());
                        } else {
                            String errorMessage = "Failed to update status: " +
                                    (response.errorBody() != null ? response.errorBody().toString() : response.message());
                            callback.onError(errorMessage);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        callback.onError("Network error: " + t.getMessage());
                    }
                });
    }

    @Override
    public void getUserStatus(String userId, final UserCallback callback) {
        apiService.getUser(userId)
                .enqueue(new Callback<User>() {
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