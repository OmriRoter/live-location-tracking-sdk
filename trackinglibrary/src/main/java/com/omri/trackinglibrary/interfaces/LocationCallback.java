package com.omri.trackinglibrary.interfaces;

import com.omri.trackinglibrary.models.Location;

/**
 * LocationCallback provides a mechanism to handle success and error responses
 * when retrieving or updating location data asynchronously.
 */
public interface LocationCallback {

    /**
     * Called when the location request is successful.
     *
     * @param location The retrieved or updated location data.
     */
    void onSuccess(Location location);

    /**
     * Called when an error occurs during the location request.
     *
     * @param error A string describing the error that occurred.
     */
    void onError(String error);
}