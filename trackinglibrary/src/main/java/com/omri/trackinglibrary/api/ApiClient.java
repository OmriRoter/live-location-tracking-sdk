package com.omri.trackinglibrary.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ApiClient is responsible for creating and providing a singleton instance of Retrofit.
 * It is configured with the base URL and a Gson converter factory for JSON serialization and deserialization.
 */
public class ApiClient {
    private static final String DEFAULT_BASE_URL = "https://live-location-tracking-backend.vercel.app/";
    private static String baseUrl = DEFAULT_BASE_URL;
    private static Retrofit retrofit = null;

    /**
     * Sets a custom base URL for the API client.
     * Must be called before the first getClient() call to take effect.
     *
     * @param url The base URL to use for API calls
     * @throws IllegalArgumentException if the URL is null or empty
     */
    public static void setBaseUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("Base URL cannot be null or empty");
        }
        // Ensure URL ends with '/'
        baseUrl = url.endsWith("/") ? url : url + "/";
        // Reset retrofit instance to create new one with new base URL
        retrofit = null;
    }

    /**
     * Gets the current base URL being used by the client.
     *
     * @return The current base URL
     */
    public static String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Provides the singleton instance of Retrofit. If the instance is not created, it initializes it.
     *
     * @return A singleton Retrofit instance configured with the base URL and Gson converter.
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * Resets the API client to its initial state.
     * This includes resetting the base URL to default and clearing the Retrofit instance.
     */
    public static void reset() {
        baseUrl = DEFAULT_BASE_URL;
        retrofit = null;
    }
}
