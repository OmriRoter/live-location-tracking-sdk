package com.omri.trackinglibrary.api;

import com.omri.trackinglibrary.models.Location;
import com.omri.trackinglibrary.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * ApiService defines the REST API endpoints for the location tracking service.
 */
public interface ApiService {

    /**
     * Creates a new user.
     *
     * @param request The user creation request containing the username
     * @return A Call object with the created User
     */
    @POST("api/users/create")
    Call<User> createUser(@Body UserRequest request);

    /**
     * Verifies a user exists in the system.
     *
     * @param request The user verification request containing the user ID
     * @return A Call object with the verified User
     */
    @POST("api/users/verify")
    Call<User> verifyUser(@Body UserVerifyRequest request);

    /**
     * Updates a user's active status.
     *
     * @param userId The ID of the user to update
     * @param request The status update request containing the new active state
     * @return A Call object with the updated User
     */
    @PATCH("api/users/{userId}/status")
    Call<User> updateUserStatus(@Path("userId") String userId, @Body UserStatusRequest request);

    /**
     * Updates a user's location.
     *
     * @param request The location update request containing user ID and coordinates
     * @return A Call object with the updated Location
     */
    @POST("api/locations/update")
    Call<Location> updateLocation(@Body LocationUpdateRequest request);

    /**
     * Gets a user's current location.
     *
     * @param userId The ID of the user whose location to retrieve
     * @return A Call object with the user's Location
     */
    @GET("api/locations/user/{userId}")
    Call<Location> getUserLocation(@Path("userId") String userId);
}
