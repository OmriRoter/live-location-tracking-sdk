package com.omri.trackinglibrary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.omri.trackinglibrary.api.ApiService;
import com.omri.trackinglibrary.api.LocationUpdateRequest;
import com.omri.trackinglibrary.interfaces.LocationCallback;
import com.omri.trackinglibrary.models.Location;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import retrofit2.Call;

public class LocationTest {
    private ApiService apiService;
    private LocationTrackerImpl locationTracker;
    private static final String TEST_USER_ID = "test123";
    private static final double TEST_LATITUDE = 32.109333;
    private static final double TEST_LONGITUDE = 34.855499;
    private static final String TEST_TIMESTAMP = "2024-01-25T10:00:00Z";

    @Before
    public void setUp() {
        apiService = mock(ApiService.class);
        locationTracker = new LocationTrackerImpl(apiService);
    }

    @Test
    public void locationCreation_isCorrect() {
        // Arrange & Act
        Location location = new Location(TEST_USER_ID, TEST_LATITUDE, TEST_LONGITUDE, TEST_TIMESTAMP);

        // Assert
        assertEquals(TEST_USER_ID, location.getUserId());
        assertEquals(TEST_LATITUDE, location.getLatitude(), 0.000001);
        assertEquals(TEST_LONGITUDE, location.getLongitude(), 0.000001);
        assertEquals(TEST_TIMESTAMP, location.getLastUpdated());
    }

    @Test
    public void updateLocation_Success() {
        // Arrange
        Call<Location> mockCall = mock(Call.class);
        when(apiService.updateLocation(any(LocationUpdateRequest.class))).thenReturn(mockCall);

        // Capture the request to verify coordinates
        ArgumentCaptor<LocationUpdateRequest> requestCaptor = ArgumentCaptor.forClass(LocationUpdateRequest.class);

        // Act
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

        // Assert
        verify(apiService).updateLocation(requestCaptor.capture());
        LocationUpdateRequest capturedRequest = requestCaptor.getValue();
        assertEquals(TEST_USER_ID, capturedRequest.getUserId());
        assertEquals(TEST_LATITUDE, capturedRequest.getLatitude(), 0.000001);
        assertEquals(TEST_LONGITUDE, capturedRequest.getLongitude(), 0.000001);
    }

    @Test
    public void getUserLocation_Success() {
        // Arrange
        Call<Location> mockCall = mock(Call.class);
        when(apiService.getUserLocation(TEST_USER_ID)).thenReturn(mockCall);

        // Act
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

        // Assert
        verify(apiService).getUserLocation(TEST_USER_ID);
    }

    @Test
    public void defaultLocation_isCorrect() {
        // Test that default location (0,0) is handled correctly
        Location location = new Location(TEST_USER_ID, 0.0, 0.0, TEST_TIMESTAMP);
        assertEquals(0.0, location.getLatitude(), 0.000001);
        assertEquals(0.0, location.getLongitude(), 0.000001);
    }
}