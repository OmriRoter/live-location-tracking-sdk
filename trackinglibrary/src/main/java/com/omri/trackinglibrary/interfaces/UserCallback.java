package com.omri.trackinglibrary.interfaces;

import com.omri.trackinglibrary.models.User;

/**
 * UserCallback provides a mechanism to handle success and error responses
 * when performing user-related operations asynchronously.
 */
public interface UserCallback {

    /**
     * Called when the user operation is successful.
     *
     * @param user The user data returned from the operation.
     */
    void onSuccess(User user);

    /**
     * Called when an error occurs during the user operation.
     *
     * @param error A string describing the error that occurred.
     */
    void onError(String error);
}
