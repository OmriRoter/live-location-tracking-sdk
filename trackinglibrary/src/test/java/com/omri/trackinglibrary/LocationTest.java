package com.omri.trackinglibrary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
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
    private static final String TEST_USER_ID = "507f1f77bcf86cd799439011";
    private static final double TEST_LATITUDE = 32.109333;
    private static final double TEST_LONGITUDE = 34.855499;
    private static final String TEST_TIMESTAMP = "2024-01-25T10:00:00.000Z";

    @Before
    public void setUp() {
        apiService = mock(ApiService.class);
        locationTracker = new LocationTrackerImpl(apiService);
    }

    @Test
    public void locationCreation_ValidData_Success() {
        Location location = new Location(TEST_USER_ID, TEST_LATITUDE, TEST_LONGITUDE, TEST_TIMESTAMP);

        assertEquals(TEST_USER_ID, location.getUserId());
        assertEquals(TEST_LATITUDE, location.getLatitude(), 0.000001);
        assertEquals(TEST_LONGITUDE, location.getLongitude(), 0.000001);
        assertEquals(TEST_TIMESTAMP, location.getLastUpdated());
    }

    @Test
    public void locationCreation_NullUserId_ThrowsException() {
        assertThrows(IllegalStateException.class, () ->
                new Location(null, TEST_LATITUDE, TEST_LONGITUDE, TEST_TIMESTAMP)
        );
    }

    @Test
    public void locationCreation_InvalidLatitude_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Location(TEST_USER_ID, 91.0, TEST_LONGITUDE, TEST_TIMESTAMP)
        );
    }

    @Test
    public void locationCreation_InvalidLongitude_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Location(TEST_USER_ID, TEST_LATITUDE, 181.0, TEST_TIMESTAMP)
        );
    }

    @Test
    public void updateLocation_Success() {
        Call<Location> mockCall = mock(Call.class);
        when(apiService.updateLocation(any(LocationUpdateRequest.class))).thenReturn(mockCall);

        ArgumentCaptor<LocationUpdateRequest> requestCaptor = ArgumentCaptor.forClass(LocationUpdateRequest.class);

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

        verify(apiService).updateLocation(requestCaptor.capture());
        LocationUpdateRequest capturedRequest = requestCaptor.getValue();
        assertEquals(TEST_USER_ID, capturedRequest.getUserId());
        assertEquals(TEST_LATITUDE, capturedRequest.getLatitude(), 0.000001);
        assertEquals(TEST_LONGITUDE, capturedRequest.getLongitude(), 0.000001);
    }

    @Test
    public void getUserLocation_Success() {
        Call<Location> mockCall = mock(Call.class);
        when(apiService.getUserLocation(TEST_USER_ID)).thenReturn(mockCall);

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

        verify(apiService).getUserLocation(TEST_USER_ID);
    }

    @Test
    public void defaultLocation_IsValid() {
        Location location = new Location(TEST_USER_ID, 0.0, 0.0, TEST_TIMESTAMP);
        assertEquals(0.0, location.getLatitude(), 0.000001);
        assertEquals(0.0, location.getLongitude(), 0.000001);
    }

    @Test
    public void updateLocation_InvalidCoordinates_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                locationTracker.updateLocation(TEST_USER_ID, 91.0, 34.855499, new LocationCallback() {
                    @Override
                    public void onSuccess(Location location) {
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