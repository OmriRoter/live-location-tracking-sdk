package com.omri.trackinglibrary.api;

import com.omri.trackinglibrary.models.Location;
import com.omri.trackinglibrary.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/api/users/create")
    Call<User> createUser(@Body UserRequest request);

    @PATCH("/api/users/{userId}/status")
    Call<User> updateUserStatus(@Path("userId") String userId, @Body UserStatusRequest request);

    @GET("/api/users/{userId}")
    Call<User> getUser(@Path("userId") String userId);

    @POST("/api/locations/update")
    Call<Location> updateLocation(@Body LocationUpdateRequest request);

    @GET("/api/locations/user/{userId}")
    Call<Location> getUserLocation(@Path("userId") String userId);
}