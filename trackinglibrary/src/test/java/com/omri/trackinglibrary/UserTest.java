package com.omri.trackinglibrary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import com.omri.trackinglibrary.api.ApiService;
import com.omri.trackinglibrary.api.UserRequest;
import com.omri.trackinglibrary.api.UserStatusRequest;
import com.omri.trackinglibrary.interfaces.UserCallback;
import com.omri.trackinglibrary.models.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import retrofit2.Call;

public class UserTest {
    private ApiService apiService;
    private LocationTrackerImpl locationTracker;
    private static final String TEST_USER_ID = "test123";
    private static final String TEST_USERNAME = "testUser";

    @Before
    public void setUp() {
        apiService = mock(ApiService.class);
        locationTracker = new LocationTrackerImpl(apiService);
    }

    @Test
    public void userCreation_isCorrect() {
        User user = new User(TEST_USER_ID, TEST_USERNAME, "2024-01-25", true);
        assertEquals(TEST_USER_ID, user.getId());
        assertEquals(TEST_USERNAME, user.getUsername());
        assertEquals(true, user.isActive());
    }

    @Test
    public void createUser_Success() {
        // Arrange
        Call<User> mockCall = mock(Call.class);
        when(apiService.createUser(any(UserRequest.class))).thenReturn(mockCall);

        // Capture the request to verify username
        ArgumentCaptor<UserRequest> requestCaptor = ArgumentCaptor.forClass(UserRequest.class);

        // Act
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

        // Assert
        verify(apiService).createUser(requestCaptor.capture());
        assertEquals(TEST_USERNAME, requestCaptor.getValue().getUsername());
    }

    @Test
    public void updateUserStatus_Success() {
        // Arrange
        Call<User> mockCall = mock(Call.class);
        when(apiService.updateUserStatus(eq(TEST_USER_ID), any(UserStatusRequest.class)))
                .thenReturn(mockCall);

        // Capture the request to verify status
        ArgumentCaptor<UserStatusRequest> requestCaptor = ArgumentCaptor.forClass(UserStatusRequest.class);

        // Act
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

        // Assert
        verify(apiService).updateUserStatus(eq(TEST_USER_ID), requestCaptor.capture());
        assertEquals(true, requestCaptor.getValue().isActive());
    }

    @Test
    public void getUserStatus_Success() {
        // Arrange
        Call<User> mockCall = mock(Call.class);
        when(apiService.getUser(TEST_USER_ID)).thenReturn(mockCall);

        // Act
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

        // Assert
        verify(apiService).getUser(TEST_USER_ID);
    }

    @Test
    public void createUser_Error() {
        // Arrange
        Call<User> mockCall = mock(Call.class);
        when(apiService.createUser(any(UserRequest.class))).thenReturn(mockCall);

        // Act
        locationTracker.createUser("", new UserCallback() {
            @Override
            public void onSuccess(User user) {
                fail("Should not reach success callback with empty username");
            }

            @Override
            public void onError(String error) {
                // Test passes
            }
        });
    }
}