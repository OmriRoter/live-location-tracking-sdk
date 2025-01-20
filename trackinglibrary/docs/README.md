# Location Tracking Android Library

A lightweight Android library for real-time location tracking, built on top of the Live Location Tracking API.

## Features

- Easy user creation and management
- Real-time location updates
- Location tracking for specific users
- Built with Retrofit for reliable API communication
- Simple integration process

## Installation

Add JitPack repository to your project's `settings.gradle`:

```kotlin
dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}
```

Add the dependency to your module's `build.gradle`:

```kotlin
dependencies {
    implementation("com.github.yourusername:trackinglibrary:1.0.0")
}
```

## Usage

### Initialize the Library

```java
LocationTracker tracker = new LocationTrackerImpl();
```

### Create a New User

```java
tracker.createUser("username", new UserCallback() {
    @Override
    public void onSuccess(User user) {
        String userId = user.getId();
        // Store userId for future use
    }

    @Override
    public void onError(String error) {
        // Handle error
    }
});
```

### Update User Location

```java
tracker.updateLocation(userId, latitude, longitude, new LocationCallback() {
    @Override
    public void onSuccess(Location location) {
        // Location updated successfully
    }

    @Override
    public void onError(String error) {
        // Handle error
    }
});
```

### Get User Location

```java
tracker.getUserLocation(userId, new LocationCallback() {
    @Override
    public void onSuccess(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        // Use the location data
    }

    @Override
    public void onError(String error) {
        // Handle error
    }
});
```

## Best Practices

1. User ID Management:
    - Store user IDs securely
    - Handle user IDs with care
    - Don't expose user IDs in logs

2. Location Updates:
    - Update frequency: 5-10 seconds recommended
    - Validate location data before sending
    - Handle location permissions properly

3. Error Handling:
    - Always implement error callbacks
    - Handle network issues gracefully
    - Show user-friendly error messages

## Required Permissions

Add these permissions to your AndroidManifest.xml:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

## Demo Application

Check out our [demo application](link-to-demo-app) for a complete implementation example.

## API Documentation

For detailed API documentation, please visit our [API Documentation](docs/api/README.md).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Please read our [Contributing Guidelines](CONTRIBUTING.md) for details.

## Support

For support, please create an issue in the GitHub repository.

## Acknowledgments

- Built on top of [Live Location Tracking API](https://live-location-tracking-backend.vercel.app)
- Uses [Retrofit](https://square.github.io/retrofit/) for API communication

