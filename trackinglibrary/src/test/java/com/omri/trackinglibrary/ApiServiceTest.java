package com.omri.trackinglibrary;

import static org.junit.Assert.*;


import com.omri.trackinglibrary.api.*;
import com.omri.trackinglibrary.models.Location;
import com.omri.trackinglibrary.models.User;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Test suite for the ApiService interface.
 * Includes both unit tests for API call object creation and integration tests for full API functionality.
 * Integration tests require a running server instance at the URL specified in ApiClient.
 */
public class ApiServiceTest {
    private ApiService apiService;

    /**
     * Sample user ID for demonstration purposes.
     * This ID is used in basic call creation tests.
     * Integration tests will create their own users.
     */
    private static final String TEST_USER_ID = "507f1f77bcf86cd799439011";

    /**
     * Sets up the test environment before each test.
     * Initializes the ApiService instance using the default ApiClient configuration.
     */
    @Before
    public void setUp() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    /**
     * Tests that createUser endpoint creates a valid Call object.
     */
    @Test
    public void createUser_ReturnsCall() {
        UserRequest request = new UserRequest("testUser");
        Call<User> call = apiService.createUser(request);
        assertNotNull("Create user call should not be null", call);
    }

    /**
     * Tests that verifyUser endpoint creates a valid Call object.
     */
    @Test
    public void verifyUser_ReturnsCall() {
        UserVerifyRequest request = new UserVerifyRequest(TEST_USER_ID);
        Call<User> call = apiService.verifyUser(request);
        assertNotNull("Verify user call should not be null", call);
    }

    /**
     * Tests that updateUserStatus endpoint creates a valid Call object.
     */
    @Test
    public void updateUserStatus_ReturnsCall() {
        UserStatusRequest request = new UserStatusRequest(true);
        Call<User> call = apiService.updateUserStatus(TEST_USER_ID, request);
        assertNotNull("Update user status call should not be null", call);
    }

    /**
     * Tests that updateLocation endpoint creates a valid Call object.
     */
    @Test
    public void updateLocation_ReturnsCall() {
        LocationUpdateRequest request = new LocationUpdateRequest(TEST_USER_ID, 32.109333, 34.855499);
        Call<Location> call = apiService.updateLocation(request);
        assertNotNull("Update location call should not be null", call);
    }

    /**
     * Tests that getUserLocation endpoint creates a valid Call object.
     */
    @Test
    public void getUserLocation_ReturnsCall() {
        Call<Location> call = apiService.getUserLocation(TEST_USER_ID);
        assertNotNull("Get user location call should not be null", call);
    }

    /**
     * Comprehensive integration test that verifies the complete user and location tracking flow:
     * 1. Creates a new user
     * 2. Verifies the user exists
     * 3. Updates the user's location
     * 4. Retrieves the user's location
     * 5. Updates the user's active status
     * 6. Verifies the status change

     * Requirements:
     * - Server must be running at the URL specified in ApiClient
     * - Network connection must be available
     * - Server must be able to handle all API endpoints
     *
     * @throws IOException if there are network communication issues
     */
    @Test
    public void integrationTest_fullFlow() throws IOException {
        // Create user with unique username
        String randomUsername = "testIntegration_" + System.currentTimeMillis();
        UserRequest createRequest = new UserRequest(randomUsername);

        Response<User> createResponse = apiService.createUser(createRequest).execute();
        assertNotNull("Response from createUser is null", createResponse);
        assertTrue(
                "Create user request failed with code: " + createResponse.code(),
                createResponse.isSuccessful()
        );

        User createdUser = createResponse.body();
        assertNotNull("Created user is null", createdUser);
        assertNotNull("Created user ID is null", createdUser.getId());
        assertEquals("Username mismatch", randomUsername, createdUser.getUsername());

        // Verify created user exists
        UserVerifyRequest verifyRequest = new UserVerifyRequest(createdUser.getId());
        Response<User> verifyResponse = apiService.verifyUser(verifyRequest).execute();
        assertNotNull("Verify user response is null", verifyResponse);
        assertTrue(
                "Verify user request failed with code: " + verifyResponse.code(),
                verifyResponse.isSuccessful()
        );

        User verifiedUser = verifyResponse.body();
        assertNotNull("Verified user is null", verifiedUser);
        assertEquals("User IDs differ after verify", createdUser.getId(), verifiedUser.getId());

        // Update user location
        double testLat = 32.098424;
        double testLon = 34.802374;
        LocationUpdateRequest locationUpdateRequest =
                new LocationUpdateRequest(createdUser.getId(), testLat, testLon);
        Response<Location> locResponse = apiService.updateLocation(locationUpdateRequest).execute();
        assertNotNull("Location update response is null", locResponse);
        assertTrue(
                "Location update request failed with code: " + locResponse.code(),
                locResponse.isSuccessful()
        );

        Location updatedLocation = locResponse.body();
        assertNotNull("Updated location is null", updatedLocation);
        assertEquals(createdUser.getId(), updatedLocation.getUserId());
        assertEquals(testLat, updatedLocation.getLatitude(), 0.00001);
        assertEquals(testLon, updatedLocation.getLongitude(), 0.00001);

        // Retrieve user location
        Response<Location> getLocResponse = apiService.getUserLocation(createdUser.getId()).execute();
        assertNotNull("Get location response is null", getLocResponse);
        assertTrue(
                "Get location request failed with code: " + getLocResponse.code(),
                getLocResponse.isSuccessful()
        );

        Location gotLocation = getLocResponse.body();
        assertNotNull("Got location is null", gotLocation);
        assertEquals("User ID mismatch in location retrieval", createdUser.getId(), gotLocation.getUserId());
        assertEquals(testLat, gotLocation.getLatitude(), 0.00001);
        assertEquals(testLon, gotLocation.getLongitude(), 0.00001);

        // Update user status
        UserStatusRequest statusRequest = new UserStatusRequest(false);
        Response<User> statusResponse = apiService.updateUserStatus(createdUser.getId(), statusRequest).execute();
        assertNotNull("Status update response is null", statusResponse);
        assertTrue(
                "Status update request failed with code: " + statusResponse.code(),
                statusResponse.isSuccessful()
        );

        User userWithUpdatedStatus = statusResponse.body();
        assertNotNull("User after status update is null", userWithUpdatedStatus);
        assertFalse("User isActive should be false after update", userWithUpdatedStatus.isActive());
        assertEquals("User ID mismatch after status update", createdUser.getId(), userWithUpdatedStatus.getId());

        // Verify status change
        UserVerifyRequest verifyAgainRequest = new UserVerifyRequest(createdUser.getId());
        Response<User> verifyAgainResponse = apiService.verifyUser(verifyAgainRequest).execute();
        assertTrue(
                "Verify user (2nd time) request failed with code: " + verifyAgainResponse.code(),
                verifyAgainResponse.isSuccessful()
        );
        User userAfterVerifyAgain = verifyAgainResponse.body();
        assertNotNull(userAfterVerifyAgain);
        assertFalse("User isActive should remain false", userAfterVerifyAgain.isActive());
    }
}