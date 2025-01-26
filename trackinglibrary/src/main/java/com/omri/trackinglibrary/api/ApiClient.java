package com.omri.trackinglibrary.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ApiClient is responsible for creating and providing a singleton instance of Retrofit.
 * Retrofit is configured with the base URL and a Gson converter factory for JSON serialization and deserialization.
 */
public class ApiClient {
    // Base URL for the API
    private static final String BASE_URL = "https://live-location-tracking-backend.vercel.app";

    // Singleton instance of Retrofit
    private static Retrofit retrofit = null;

    /**
     * Provides the singleton instance of Retrofit. If the instance is not created, it initializes it.
     *
     * @return A singleton Retrofit instance configured with the base URL and Gson converter.
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}