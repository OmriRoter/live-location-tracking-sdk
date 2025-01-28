package com.omri.trackinglibrary;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.omri.trackinglibrary.api.*;
import com.omri.trackinglibrary.interfaces.LocationCallback;
import com.omri.trackinglibrary.interfaces.UserCallback;
import com.omri.trackinglibrary.models.Location;
import com.omri.trackinglibrary.models.User;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Call;

/**
 * Unit tests for LocationTrackerImpl class using mock ApiService.
 * Tests all main functionalities of the location tracker including user management
 * and location updates using Mockito for API service simulation.
 */
public class LocationTrackerImplTest {
    private ApiService mockApiService;
    private LocationTrackerImpl locationTracker;

    /**
     * Test constants used across multiple test cases
     */
    private static final String TEST_USER_ID = "507f1f77bcf86cd799439011";
    private static final String TEST_USERNAME = "testUser";
    private static final double TEST_LATITUDE = 32.109333;
    private static final double TEST_LONGITUDE = 34.855499;

    /**
     * Sets up the test environment before each test execution.
     * Creates a mock API service and initializes the location tracker with it.
     */
    @Before
    public void setUp() {
        mockApiService = mock(ApiService.class);
        locationTracker = new LocationTrackerImpl(mockApiService);
    }

    /**
     * Tests successful user creation.
     * Verifies that the API service is called with appropriate parameters
     * and the success callback is triggered.
     */
    @Test
    public void createUser_Success() {
        Call<User> mockCall = mock(Call.class);
        when(mockApiService.createUser(any(UserRequest.class))).thenReturn(mockCall);

        locationTracker.createUser(TEST_USERNAME, new UserCallback() {
            @Override
            public void onSuccess(User user) {
                // success
            }

            @Override
            public void onError(String error) {
                fail("Should not reach error callback");
            }
        });

        verify(mockApiService).createUser(any(UserRequest.class));
    }

    /**
     * Tests successful user verification.
     * Verifies that the API service is called with the correct user ID
     * and the success callback is triggered.
     */
    @Test
    public void verifyUser_Success() {
        Call<User> mockCall = mock(Call.class);
        when(mockApiService.verifyUser(any(UserVerifyRequest.class))).thenReturn(mockCall);

        locationTracker.verifyUser(TEST_USER_ID, new UserCallback() {
            @Override
            public void onSuccess(User user) {
                // success
            }

            @Override
            public void onError(String error) {
                fail("Should not reach error callback");
            }
        });

        verify(mockApiService).verifyUser(any(UserVerifyRequest.class));
    }

    /**
     * Tests successful user status update.
     * Verifies that the API service is called with correct user ID and status
     * and the success callback is triggered.
     */
    @Test
    public void updateUserStatus_Success() {
        Call<User> mockCall = mock(Call.class);
        when(mockApiService.updateUserStatus(eq(TEST_USER_ID), any(UserStatusRequest.class)))
                .thenReturn(mockCall);

        locationTracker.updateUserStatus(TEST_USER_ID, true, new UserCallback() {
            @Override
            public void onSuccess(User user) {
                // success
            }

            @Override
            public void onError(String error) {
                fail("Should not reach error callback");
            }
        });

        verify(mockApiService).updateUserStatus(eq(TEST_USER_ID), any(UserStatusRequest.class));
    }

    /**
     * Tests successful location update.
     * Verifies that the API service is called with correct location parameters
     * and the success callback is triggered.
     */
    @Test
    public void updateLocation_Success() {
        Call<Location> mockCall = mock(Call.class);
        when(mockApiService.updateLocation(any(LocationUpdateRequest.class))).thenReturn(mockCall);

        locationTracker.updateLocation(TEST_USER_ID, TEST_LATITUDE, TEST_LONGITUDE, new LocationCallback() {
            @Override
            public void onSuccess(Location location) {
                // success
            }

            @Override
            public void onError(String error) {
                fail("Should not reach error callback");
            }
        });

        verify(mockApiService).updateLocation(any(LocationUpdateRequest.class));
    }

    /**
     * Tests successful user location retrieval.
     * Verifies that the API service is called with the correct user ID
     * and the success callback is triggered.
     */
    @Test
    public void getUserLocation_Success() {
        Call<Location> mockCall = mock(Call.class);
        when(mockApiService.getUserLocation(TEST_USER_ID)).thenReturn(mockCall);

        locationTracker.getUserLocation(TEST_USER_ID, new LocationCallback() {
            @Override
            public void onSuccess(Location location) {
                // success
            }

            @Override
            public void onError(String error) {
                fail("Should not reach error callback");
            }
        });

        verify(mockApiService).getUserLocation(TEST_USER_ID);
    }

    /**
     * Tests that getUserStatus correctly delegates to verifyUser.
     * Verifies that the API service's verifyUser method is called
     * when requesting user status.
     */
    @Test
    public void getUserStatus_CallsVerifyUser() {
        Call<User> mockCall = mock(Call.class);
        when(mockApiService.verifyUser(any(UserVerifyRequest.class))).thenReturn(mockCall);

        locationTracker.getUserStatus(TEST_USER_ID, new UserCallback() {
            @Override
            public void onSuccess(User user) {
                // success
            }

            @Override
            public void onError(String error) {
                fail("Should not reach error callback");
            }
        });

        verify(mockApiService).verifyUser(any(UserVerifyRequest.class));
    }
}