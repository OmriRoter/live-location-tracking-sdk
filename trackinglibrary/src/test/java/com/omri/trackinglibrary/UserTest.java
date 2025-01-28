package com.omri.trackinglibrary;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.eq;

import com.omri.trackinglibrary.api.*;
import com.omri.trackinglibrary.interfaces.UserCallback;
import com.omri.trackinglibrary.models.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import retrofit2.Call;

/**
 * Test suite for User-related functionality.
 * Tests user creation, validation, and API interactions using mocked services.
 * Covers both successful scenarios and error cases.
 */
public class UserTest {
    private ApiService apiService;
    private LocationTrackerImpl locationTracker;

    /**
     * Test constants used across test cases
     */
    private static final String TEST_USER_ID = "507f1f77bcf86cd799439011";
    private static final String TEST_USERNAME = "testUser";
    private static final String TEST_TIMESTAMP = "2024-01-25T10:00:00.000Z";

    /**
     * Sets up the test environment before each test.
     * Creates mock API service and initializes location tracker.
     */
    @Before
    public void setUp() {
        apiService = mock(ApiService.class);
        locationTracker = new LocationTrackerImpl(apiService);
    }

    /**
     * Tests successful user object creation with valid data.
     * Verifies all fields are correctly set.
     */
    @Test
    public void userCreation_ValidData_Success() {
        User user = new User(TEST_USER_ID, TEST_USERNAME, TEST_TIMESTAMP, true);
        assertEquals(TEST_USER_ID, user.getId());
        assertEquals(TEST_USERNAME, user.getUsername());
        assertTrue(user.isActive());
        assertEquals(TEST_TIMESTAMP, user.getCreatedAt());
    }

    /**
     * Tests that user creation fails with null ID.
     * Expects IllegalStateException to be thrown.
     */
    @Test
    public void userCreation_NullId_ThrowsException() {
        assertThrows(IllegalStateException.class, () ->
                new User(null, TEST_USERNAME, TEST_TIMESTAMP, true)
        );
    }

    /**
     * Tests that user creation fails with null username.
     * Expects IllegalStateException to be thrown.
     */
    @Test
    public void userCreation_NullUsername_ThrowsException() {
        assertThrows(IllegalStateException.class, () ->
                new User(TEST_USER_ID, null, TEST_TIMESTAMP, true)
        );
    }

    /**
     * Tests successful user creation through the API.
     * Verifies that the correct username is sent in the request.
     */
    @Test
    public void createUser_ValidUsername_Success() {
        Call<User> mockCall = mock(Call.class);
        when(apiService.createUser(any(UserRequest.class))).thenReturn(mockCall);

        ArgumentCaptor<UserRequest> requestCaptor = ArgumentCaptor.forClass(UserRequest.class);

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

        verify(apiService).createUser(requestCaptor.capture());
        assertEquals(TEST_USERNAME, requestCaptor.getValue().getUsername());
    }

    /**
     * Tests successful user verification through the API.
     * Verifies that the correct user ID is sent in the request.
     */
    @Test
    public void verifyUser_ValidUserId_Success() {
        Call<User> mockCall = mock(Call.class);
        when(apiService.verifyUser(any(UserVerifyRequest.class))).thenReturn(mockCall);

        ArgumentCaptor<UserVerifyRequest> requestCaptor = ArgumentCaptor.forClass(UserVerifyRequest.class);

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

        verify(apiService).verifyUser(requestCaptor.capture());
        assertEquals(TEST_USER_ID, requestCaptor.getValue().getUserId());
    }

    /**
     * Tests successful user status update through the API.
     * Verifies that the correct status is sent in the request.
     */
    @Test
    public void updateUserStatus_ValidStatus_Success() {
        Call<User> mockCall = mock(Call.class);
        when(apiService.updateUserStatus(eq(TEST_USER_ID), any(UserStatusRequest.class)))
                .thenReturn(mockCall);

        ArgumentCaptor<UserStatusRequest> requestCaptor = ArgumentCaptor.forClass(UserStatusRequest.class);

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

        verify(apiService).updateUserStatus(eq(TEST_USER_ID), requestCaptor.capture());
        assertTrue(requestCaptor.getValue().isActive());
    }

    /**
     * Tests that user creation fails with empty username.
     * Expects IllegalArgumentException to be thrown before API call.
     */
    @Test
    public void createUser_EmptyUsername_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                locationTracker.createUser("", new UserCallback() {
                    @Override
                    public void onSuccess(User user) {
                        fail("Should not reach success callback");
                    }

                    @Override
                    public void onError(String error) {
                        // Expected to throw exception before reaching this callback
                    }
                })
        );
    }

    /**
     * Tests that user verification fails with empty user ID.
     * Expects IllegalArgumentException to be thrown before API call.
     */
    @Test
    public void verifyUser_EmptyUserId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                locationTracker.verifyUser("", new UserCallback() {
                    @Override
                    public void onSuccess(User user) {
                        fail("Should not reach success callback");
                    }

                    @Override
                    public void onError(String error) {
                        // Expected to throw exception before reaching this callback
                    }
                })
        );
    }

    /**
     * Tests that user creation fails with username longer than 50 characters.
     * Expects IllegalArgumentException to be thrown before API call.
     */
    @Test
    public void createUser_VeryLongUsername_ThrowsException() {
        String longUsername = "a".repeat(51); // Creates username longer than 50 characters
        assertThrows(IllegalArgumentException.class, () ->
                locationTracker.createUser(longUsername, new UserCallback() {
                    @Override
                    public void onSuccess(User user) {
                        fail("Should not reach success callback");
                    }

                    @Override
                    public void onError(String error) {
                        // Expected to throw exception before reaching this callback
                    }
                })
        );
    }
}