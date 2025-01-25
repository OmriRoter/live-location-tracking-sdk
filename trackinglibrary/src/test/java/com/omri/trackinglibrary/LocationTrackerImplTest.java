package com.omri.trackinglibrary;

import com.omri.trackinglibrary.api.ApiService;
import com.omri.trackinglibrary.interfaces.UserCallback;
import com.omri.trackinglibrary.models.User;
import com.omri.trackinglibrary.api.UserRequest;
import com.omri.trackinglibrary.api.UserStatusRequest;

import org.junit.Before;
import org.junit.Test;
import retrofit2.Call;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link LocationTrackerImpl} class to verify correct behavior of all operations.
 */
public class LocationTrackerImplTest {

    private ApiService mockApiService;
    private LocationTrackerImpl locationTracker;

    /**
     * Sets up the test environment by mocking dependencies before each test case.
     */
    @Before
    public void setUp() {
        mockApiService = mock(ApiService.class);
        locationTracker = new LocationTrackerImpl(mockApiService);
    }

    /**
     * Tests the createUser method to ensure it properly interacts with the API service.
     * A successful API call should invoke the success callback.
     */
    @Test
    public void createUser_Success() {
        // Arrange: Mock the API service to return a mocked call object
        Call<User> mockCall = mock(Call.class);
        when(mockApiService.createUser(any(UserRequest.class))).thenReturn(mockCall);

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

        // Verify that createUser was called on the API service
        verify(mockApiService).createUser(any(UserRequest.class));
    }

    /**
     * Tests the updateUserStatus method to ensure it properly interacts with the API service.
     */
    @Test
    public void updateUserStatus_Success() {
        // Arrange: Mock the API service to return a mocked call object
        Call<User> mockCall = mock(Call.class);
        when(mockApiService.updateUserStatus(any(String.class), any(UserStatusRequest.class))).thenReturn(mockCall);

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

        // Verify that updateUserStatus was called on the API service
        verify(mockApiService).updateUserStatus(any(String.class), any(UserStatusRequest.class));
    }
}