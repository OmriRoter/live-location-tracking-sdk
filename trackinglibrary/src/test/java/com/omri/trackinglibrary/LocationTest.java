package com.omri.trackinglibrary;

import com.omri.trackinglibrary.models.Location;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the {@link Location} class to ensure correct object creation and data retrieval.
 */
public class LocationTest {

    /**
     * Tests the creation of a {@link Location} object and verifies that the values
     * are correctly assigned and retrieved via getter methods.
     */
    @Test
    public void locationCreation_isCorrect() {
        // Arrange: Create a Location object with test data
        Location location = new Location("123", 32.109333, 34.855499, "2024-01-20");

        // Assert: Verify that the values are correctly set and retrieved
        assertEquals("123", location.getUserId());
        assertEquals(32.109333, location.getLatitude(), 0.000001);
        assertEquals(34.855499, location.getLongitude(), 0.000001);
        assertEquals("2024-01-20", location.getLastUpdated());
    }

    /**
     * Tests that default location values (0.0, 0.0) are handled correctly
     */
    @Test
    public void defaultLocationCreation_isCorrect() {
        // Arrange: Create a Location object with default coordinates
        Location location = new Location("123", 0.0, 0.0, "2024-01-20");

        // Assert: Verify that default coordinates are correctly set and retrieved
        assertEquals("123", location.getUserId());
        assertEquals(0.0, location.getLatitude(), 0.000001);
        assertEquals(0.0, location.getLongitude(), 0.000001);
        assertEquals("2024-01-20", location.getLastUpdated());
    }
}