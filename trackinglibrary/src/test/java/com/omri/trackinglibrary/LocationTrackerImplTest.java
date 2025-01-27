package com.omri.trackinglibrary;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.omri.trackinglibrary.api.ApiService;
import com.omri.trackinglibrary.api.LocationUpdateRequest;
import com.omri.trackinglibrary.api.UserRequest;
import com.omri.trackinglibrary.api.UserStatusRequest;
import com.omri.trackinglibrary.api.UserVerifyRequest;
import com.omri.trackinglibrary.interfaces.LocationCallback;
import com.omri.trackinglibrary.interfaces.UserCallback;
import com.omri.trackinglibrary.models.Location;
import com.omri.trackinglibrary.models.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import retrofit2.Call;

public class LocationTrackerImplTest {
    private ApiService mockApiService;
    private LocationTrackerImpl locationTracker;
    private static final String TEST_USER_ID = "507f1f77bcf86cd799439011";
    private static final String TEST_USERNAME = "testUser";
    private static final double TEST_LATITUDE = 32.109333;
    private static final double TEST_LONGITUDE = 34.855499;

    @Before
    public void setUp() {
        mockApiService = mock(ApiService.class);
        locationTracker = new LocationTrackerImpl(mockApiService);
    }

    @Test
    public void createUser_Success() {
        Call<User> mockCall = mock(Call.class);
        when(mockApiService.createUser(any(UserRequest.class))).thenReturn(mockCall);

        locationTracker.createUser(TEST_USERNAME, new UserCallback() {
            @Override
            public void onSuccess(User user) {
                // Test passes
            }

            @Override
            public void onError(String error) {
                fail("Should not reach error callback");
            }
        });

        verify(mockApiService).createUser(any(UserRequest.class));
    }

    @Test
    public void verifyUser_Success() {
        Call<User> mockCall = mock(Call.class);
        when(mockApiService.verifyUser(any(UserVerifyRequest.class))).thenReturn(mockCall);

        locationTracker.verifyUser(TEST_USER_ID, new UserCallback() {
            @Override
            public void onSuccess(User user) {
                // Test passes
            }

            @Override
            public void onError(String error) {
                fail("Should not reach error callback");
            }
        });

        verify(mockApiService).verifyUser(any(UserVerifyRequest.class));
    }

    @Test
    public void updateUserStatus_Success() {
        Call<User> mockCall = mock(Call.class);
        when(mockApiService.updateUserStatus(eq(TEST_USER_ID), any(UserStatusRequest.class)))
                .thenReturn(mockCall);

        ArgumentCaptor<UserStatusRequest> requestCaptor = ArgumentCaptor.forClass(UserStatusRequest.class);

        locationTracker.updateUserStatus(TEST_USER_ID, true, new UserCallback() {
            @Override
            public void onSuccess(User user) {
                // Test passes
            }

            @Override
            public void onError(String error) {
                fail("Should not reach error callback");
            }
        });

        verify(mockApiService).updateUserStatus(eq(TEST_USER_ID), requestCaptor.capture());
    }

    @Test
    public void updateLocation_Success() {
        Call<Location> mockCall = mock(Call.class);
        when(mockApiService.updateLocation(any(LocationUpdateRequest.class))).thenReturn(mockCall);

        locationTracker.updateLocation(TEST_USER_ID, TEST_LATITUDE, TEST_LONGITUDE, new LocationCallback() {
            @Override
            public void onSuccess(Location location) {
                // Test passes
            }

            @Override
            public void onError(String error) {
                fail("Should not reach error callback");
            }
        });

        verify(mockApiService).updateLocation(any(LocationUpdateRequest.class));
    }

    @Test
    public void getUserLocation_Success() {
        Call<Location> mockCall = mock(Call.class);
        when(mockApiService.getUserLocation(TEST_USER_ID)).thenReturn(mockCall);

        locationTracker.getUserLocation(TEST_USER_ID, new LocationCallback() {
            @Override
            public void onSuccess(Location location) {
                // Test passes
            }

            @Override
            public void onError(String error) {
                fail("Should not reach error callback");
            }
        });

        verify(mockApiService).getUserLocation(TEST_USER_ID);
    }

    @Test
    public void getUserStatus_CallsVerifyUser() {
        Call<User> mockCall = mock(Call.class);
        when(mockApiService.verifyUser(any(UserVerifyRequest.class))).thenReturn(mockCall);

        locationTracker.getUserStatus(TEST_USER_ID, new UserCallback() {
            @Override
            public void onSuccess(User user) {
                // Test passes
            }

            @Override
            public void onError(String error) {
                fail("Should not reach error callback");
            }
        });

        verify(mockApiService).verifyUser(any(UserVerifyRequest.class));
    }
}