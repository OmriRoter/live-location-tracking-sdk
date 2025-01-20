package com.omri.trackinglibrary.api;

import com.omri.trackinglibrary.models.Location;
import com.omri.trackinglibrary.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * ApiService defines the endpoints for user and location-related operations.
 * It provides methods to create users, update locations, and retrieve user locations.
 */
public interface ApiService {

    /**
     * Sends a request to create a new user in the system.
     *
     * @param request The user request object containing user details.
     * @return A Call object to execute the request asynchronously or synchronously.
     */
    @POST("/api/users/create")
    Call<User> createUser(@Body UserRequest request);

    /**
     * Sends a request to update the location of a user.
     *
     * @param request The location update request containing updated location details.
     * @return A Call object to execute the request asynchronously or synchronously.
     */
    @POST("/api/locations/update")
    Call<Location> updateLocation(@Body LocationUpdateRequest request);

    /**
     * Retrieves the location of a user based on their unique user ID.
     *
     * @param userId The unique identifier of the user.
     * @return A Call object containing the user's location details.
     */
    @GET("/api/locations/user/{userId}")
    Call<Location> getUserLocation(@Path("userId") String userId);
}
