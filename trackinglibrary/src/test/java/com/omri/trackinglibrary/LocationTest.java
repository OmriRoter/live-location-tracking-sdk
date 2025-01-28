package com.omri.trackinglibrary;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.omri.trackinglibrary.api.ApiService;
import com.omri.trackinglibrary.api.LocationUpdateRequest;
import com.omri.trackinglibrary.interfaces.LocationCallback;
import com.omri.trackinglibrary.models.Location;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import retrofit2.Call;

/**
 * Test suite for Location-related functionality.
 * Tests location creation, validation, and API interactions using mocked services.
 */
public class LocationTest {
    private ApiService apiService;
    private LocationTrackerImpl locationTracker;

    /**
     * Test constants for location testing
     */
    private static final String TEST_USER_ID = "507f1f77bcf86cd799439011";
    private static final double TEST_LATITUDE = 32.109333;
    private static final double TEST_LONGITUDE = 34.855499;
    private static final String TEST_TIMESTAMP = "2024-01-25T10:00:00.000Z";

    /**
     * Sets up the test environment before each test.
     * Creates mock API service and initializes location tracker with it.
     */
    @Before
    public void setUp() {
        apiService = mock(ApiService.class);
        locationTracker = new LocationTrackerImpl(apiService);
    }

    /**
     * Tests successful location object creation with valid data.
     * Verifies all fields are correctly set.
     */
    @Test
    public void locationCreation_ValidData_Success() {
        Location location = new Location(TEST_USER_ID, TEST_LATITUDE, TEST_LONGITUDE, TEST_TIMESTAMP);

        assertEquals(TEST_USER_ID, location.getUserId());
        assertEquals(TEST_LATITUDE, location.getLatitude(), 0.000001);
        assertEquals(TEST_LONGITUDE, location.getLongitude(), 0.000001);
        assertEquals(TEST_TIMESTAMP, location.getLastUpdated());
    }

    /**
     * Tests that location creation fails with null user ID.
     * Expects IllegalStateException to be thrown.
     */
    @Test
    public void locationCreation_NullUserId_ThrowsException() {
        assertThrows(IllegalStateException.class, () ->
                new Location(null, TEST_LATITUDE, TEST_LONGITUDE, TEST_TIMESTAMP)
        );
    }

    /**
     * Tests that location creation fails with invalid latitude (>90).
     * Expects IllegalArgumentException to be thrown.
     */
    @Test
    public void locationCreation_InvalidLatitude_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Location(TEST_USER_ID, 91.0, TEST_LONGITUDE, TEST_TIMESTAMP)
        );
    }

    /**
     * Tests that location creation fails with invalid longitude (>180).
     * Expects IllegalArgumentException to be thrown.
     */
    @Test
    public void locationCreation_InvalidLongitude_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Location(TEST_USER_ID, TEST_LATITUDE, 181.0, TEST_TIMESTAMP)
        );
    }

    /**
     * Tests successful location update through the API.
     * Verifies that the correct request parameters are sent.
     */
    @Test
    public void updateLocation_Success() {
        Call<Location> mockCall = mock(Call.class);
        when(apiService.updateLocation(any(LocationUpdateRequest.class))).thenReturn(mockCall);

        ArgumentCaptor<LocationUpdateRequest> requestCaptor = ArgumentCaptor.forClass(LocationUpdateRequest.class);

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

        verify(apiService).updateLocation(requestCaptor.capture());
        LocationUpdateRequest capturedRequest = requestCaptor.getValue();
        assertEquals(TEST_USER_ID, capturedRequest.getUserId());
        assertEquals(TEST_LATITUDE, capturedRequest.getLatitude(), 0.000001);
        assertEquals(TEST_LONGITUDE, capturedRequest.getLongitude(), 0.000001);
    }

    /**
     * Tests successful retrieval of user location through the API.
     * Verifies that the correct user ID is used in the request.
     */
    @Test
    public void getUserLocation_Success() {
        Call<Location> mockCall = mock(Call.class);
        when(apiService.getUserLocation(TEST_USER_ID)).thenReturn(mockCall);

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

        verify(apiService).getUserLocation(TEST_USER_ID);
    }

    /**
     * Tests that a location with coordinates (0,0) is considered valid.
     * This is important for locations at the prime meridian and equator.
     */
    @Test
    public void defaultLocation_IsValid() {
        Location location = new Location(TEST_USER_ID, 0.0, 0.0, TEST_TIMESTAMP);
        assertEquals(0.0, location.getLatitude(), 0.000001);
        assertEquals(0.0, location.getLongitude(), 0.000001);
    }

    /**
     * Tests that location update with invalid coordinates fails before making API call.
     * Expects IllegalArgumentException to be thrown immediately.
     */
    @Test
    public void updateLocation_InvalidCoordinates_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                locationTracker.updateLocation(TEST_USER_ID, 91.0, TEST_LONGITUDE, new LocationCallback() {
                    @Override
                    public void onSuccess(Location location) {
                        fail("Should not reach success callback");
                    }

                    @Override
                    public void onError(String error) {
                        // Even if we had a callback, code won't reach here due to exception
                    }
                })
        );
    }
}