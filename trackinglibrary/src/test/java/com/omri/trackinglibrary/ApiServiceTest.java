package com.omri.trackinglibrary;

import static org.junit.Assert.assertNotNull;

import com.omri.trackinglibrary.api.ApiClient;
import com.omri.trackinglibrary.api.ApiService;
import com.omri.trackinglibrary.api.LocationUpdateRequest;
import com.omri.trackinglibrary.api.UserRequest;
import com.omri.trackinglibrary.api.UserStatusRequest;
import com.omri.trackinglibrary.models.Location;
import com.omri.trackinglibrary.models.User;

import org.junit.Before;
import org.junit.Test;

import retrofit2.Call;

public class ApiServiceTest {
    private ApiService apiService;
    private static final String TEST_USER_ID = "test123";

    @Before
    public void setUp() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    @Test
    public void createUser_ReturnsCall() {
        UserRequest request = new UserRequest("testUser");
        Call<User> call = apiService.createUser(request);
        assertNotNull("Create user call should not be null", call);
    }

    @Test
    public void updateUserStatus_ReturnsCall() {
        UserStatusRequest request = new UserStatusRequest(true);
        Call<User> call = apiService.updateUserStatus(TEST_USER_ID, request);
        assertNotNull("Update user status call should not be null", call);
    }

    @Test
    public void getUser_ReturnsCall() {
        Call<User> call = apiService.getUser(TEST_USER_ID);
        assertNotNull("Get user call should not be null", call);
    }

    @Test
    public void updateLocation_ReturnsCall() {
        LocationUpdateRequest request = new LocationUpdateRequest(TEST_USER_ID, 0.0, 0.0);
        Call<Location> call = apiService.updateLocation(request);
        assertNotNull("Update location call should not be null", call);
    }

    @Test
    public void getUserLocation_ReturnsCall() {
        Call<Location> call = apiService.getUserLocation(TEST_USER_ID);
        assertNotNull("Get user location call should not be null", call);
    }
}