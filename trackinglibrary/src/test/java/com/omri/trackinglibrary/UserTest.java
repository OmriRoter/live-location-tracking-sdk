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

public class UserTest {
    private ApiService apiService;
    private LocationTrackerImpl locationTracker;
    private static final String TEST_USER_ID = "507f1f77bcf86cd799439011";
    private static final String TEST_USERNAME = "testUser";
    private static final String TEST_TIMESTAMP = "2024-01-25T10:00:00.000Z";

    @Before
    public void setUp() {
        apiService = mock(ApiService.class);
        locationTracker = new LocationTrackerImpl(apiService);
    }

    @Test
    public void userCreation_ValidData_Success() {
        User user = new User(TEST_USER_ID, TEST_USERNAME, TEST_TIMESTAMP, true);
        assertEquals(TEST_USER_ID, user.getId());
        assertEquals(TEST_USERNAME, user.getUsername());
        assertTrue(user.isActive());
        assertEquals(TEST_TIMESTAMP, user.getCreatedAt());
    }

    @Test
    public void userCreation_NullId_ThrowsException() {
        assertThrows(IllegalStateException.class, () ->
                new User(null, TEST_USERNAME, TEST_TIMESTAMP, true)
        );
    }

    @Test
    public void userCreation_NullUsername_ThrowsException() {
        assertThrows(IllegalStateException.class, () ->
                new User(TEST_USER_ID, null, TEST_TIMESTAMP, true)
        );
    }

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
                        // Expected
                    }
                })
        );
    }

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
                        // Expected
                    }
                })
        );
    }

    @Test
    public void createUser_VeryLongUsername_ThrowsException() {
        String longUsername = "a".repeat(51); // username longer than 50 chars
        assertThrows(IllegalArgumentException.class, () ->
                locationTracker.createUser(longUsername, new UserCallback() {
                    @Override
                    public void onSuccess(User user) {
                        fail("Should not reach success callback");
                    }

                    @Override
                    public void onError(String error) {
                        // Expected
                    }
                })
        );
    }
}
