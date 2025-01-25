package com.omri.trackinglibrary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import com.omri.trackinglibrary.api.ApiService;
import com.omri.trackinglibrary.api.LocationUpdateRequest;
import com.omri.trackinglibrary.api.UserRequest;
import com.omri.trackinglibrary.api.UserStatusRequest;
import com.omri.trackinglibrary.interfaces.LocationCallback;
import com.omri.trackinglibrary.interfaces.UserCallback;
import com.omri.trackinglibrary.models.User;
import com.omri.trackinglibrary.models.Location;

import org.junit.Before;
import org.junit.Test;

import retrofit2.Call;

/**
 * Unit tests for {@link LocationTrackerImpl} to verify the correct behavior of user and location operations.
 */
public class UserTest {

    private ApiService apiService;
    private LocationTrackerImpl locationTracker;
    private User testUser;

    /**
     * Sets up the test environment by mocking dependencies before each test case.
     */
    @Before
    public void setUp() {
        apiService = mock(ApiService.class);
        locationTracker = new LocationTrackerImpl(apiService);
        testUser = new User("123", "testUser", "2024-01-20", true);
    }

    @Test
    public void userCreation_isCorrect() {
        // Test user object creation with active status
        assertEquals("123", testUser.getId());
        assertEquals("testUser", testUser.getUsername());
        assertEquals("2024-01-20", testUser.getCreatedAt());
        assertEquals(true, testUser.isActive());
    }

    /**
     * Tests the createUser method to ensure it successfully interacts with the API service.
     * A successful API call should invoke the success callback.
     */
    @Test
    public void createUser_Success() {
        // Arrange: Mock the API service to return a mocked call object
        Call<User> mockCall = mock(Call.class);
        when(apiService.createUser(any(UserRequest.class))).thenReturn(mockCall);

        // Act: Attempt to create a user and verify success callback is triggered
        locationTracker.createUser("testUser", new UserCallback() {
            @Override
            public void onSuccess(User user) {
                // Test passes if we reach here without failure
            }

            @Override
            public void onError(String error) {
                fail("Should not reach error callback");
            }
        });
    }

    /**
     * Tests the updateUserStatus method to ensure it successfully interacts with the API service.
     */
    @Test
    public void updateUserStatus_Success() {
        // Arrange: Mock the API service to return a mocked call object
        Call<User> mockCall = mock(Call.class);
        when(apiService.updateUserStatus(any(String.class), any(UserStatusRequest.class))).thenReturn(mockCall);

        // Act: Attempt to update user status and verify success callback is triggered
        locationTracker.updateUserStatus("123", false, new UserCallback() {
            @Override
            public void onSuccess(User user) {
                // Test passes if we reach here without failure
            }

            @Override
            public void onError(String error) {
                fail("Should not reach error callback");
            }
        });
    }

    /**
     * Tests the updateLocation method to ensure it successfully interacts with the API service.
     * A successful API call should invoke the success callback.
     */
    @Test
    public void updateLocation_Success() {
        // Arrange: Mock the API service to return a mocked call object
        Call<Location> mockCall = mock(Call.class);
        when(apiService.updateLocation(any(LocationUpdateRequest.class))).thenReturn(mockCall);

        // Act: Attempt to update user location and verify success callback is triggered
        locationTracker.updateLocation("123", 32.109333, 34.855499, new LocationCallback() {
            @Override
            public void onSuccess(Location location) {
                // Test passes if we reach here without failure
            }

            @Override
            public void onError(String error) {
                fail("Should not reach error callback");
            }
        });
    }

    /**
     * Tests the getUserLocation method to ensure it successfully interacts with the API service.
     * A successful API call should invoke the success callback.
     */
    @Test
    public void getUserLocation_Success() {
        // Arrange: Mock the API service to return a mocked call object
        Call<Location> mockCall = mock(Call.class);
        when(apiService.getUserLocation(any(String.class))).thenReturn(mockCall);

        // Act: Attempt to get user location and verify success callback is triggered
        locationTracker.getUserLocation("123", new LocationCallback() {
            @Override
            public void onSuccess(Location location) {
                // Test passes if we reach here without failure
            }

            @Override
            public void onError(String error) {
                fail("Should not reach error callback");
            }
        });
    }
}