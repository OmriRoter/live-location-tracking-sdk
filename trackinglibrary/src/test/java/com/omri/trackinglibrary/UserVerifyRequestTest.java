package com.omri.trackinglibrary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.omri.trackinglibrary.api.UserVerifyRequest;

import org.junit.Test;

public class UserVerifyRequestTest {
    private static final String VALID_USER_ID = "507f1f77bcf86cd799439011";

    @Test
    public void constructor_ValidUserId_CreatesInstance() {
        UserVerifyRequest request = new UserVerifyRequest(VALID_USER_ID);
        assertEquals(VALID_USER_ID, request.getUserId());
    }

    @Test
    public void constructor_NullUserId_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new UserVerifyRequest(null)
        );
        assertEquals("User ID cannot be null or empty", exception.getMessage());
    }

    @Test
    public void constructor_EmptyUserId_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new UserVerifyRequest("")
        );
        assertEquals("User ID cannot be null or empty", exception.getMessage());
    }

    @Test
    public void constructor_BlankUserId_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new UserVerifyRequest("   ")
        );
        assertEquals("User ID cannot be null or empty", exception.getMessage());
    }
}