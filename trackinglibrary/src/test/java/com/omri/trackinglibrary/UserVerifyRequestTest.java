package com.omri.trackinglibrary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.omri.trackinglibrary.api.UserVerifyRequest;
import org.junit.Test;

/**
 * Test suite for UserVerifyRequest class.
 * Validates the creation and validation logic of user verification requests.
 */
public class UserVerifyRequestTest {
    /**
     * A valid user ID for testing successful request creation.
     * Format follows MongoDB ObjectId pattern.
     */
    private static final String VALID_USER_ID = "507f1f77bcf86cd799439011";

    /**
     * Tests successful creation of UserVerifyRequest with a valid user ID.
     * Verifies that the user ID is correctly stored in the request object.
     */
    @Test
    public void constructor_ValidUserId_CreatesInstance() {
        UserVerifyRequest request = new UserVerifyRequest(VALID_USER_ID);
        assertEquals(VALID_USER_ID, request.getUserId());
    }

    /**
     * Tests that constructor throws exception when user ID is null.
     * Verifies both the exception type and error message.
     */
    @Test
    public void constructor_NullUserId_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new UserVerifyRequest(null)
        );
        assertEquals("User ID cannot be null or empty", exception.getMessage());
    }

    /**
     * Tests that constructor throws exception when user ID is empty string.
     * Verifies both the exception type and error message.
     */
    @Test
    public void constructor_EmptyUserId_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new UserVerifyRequest("")
        );
        assertEquals("User ID cannot be null or empty", exception.getMessage());
    }

    /**
     * Tests that constructor throws exception when user ID contains only whitespace.
     * Verifies both the exception type and error message.
     */
    @Test
    public void constructor_BlankUserId_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new UserVerifyRequest("   ")
        );
        assertEquals("User ID cannot be null or empty", exception.getMessage());
    }
}