# Location Tracking Android Library

[![](https://jitpack.io/v/OmriRoter/live-location-tracking-sdk.svg)](https://jitpack.io/#OmriRoter/live-location-tracking-sdk)

A lightweight Android library for real-time location tracking, built on top of the Live Location Tracking API.

## Features

- Easy user creation and management
- User active status tracking and updates
- Real-time location updates
- Automatic default location initialization
- Location tracking for specific users
- Built with Retrofit for reliable API communication
- Simple integration process

## Installation

Add JitPack repository to your project's `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Add the dependency to your module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.OmriRoter:live-location-tracking-sdk:1.1.0")
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
        boolean isActive = user.isActive(); // Will be true by default
        // Store userId for future use
    }

    @Override
    public void onError(String error) {
        // Handle error
    }
});
```

### Update User Status

```java
tracker.updateUserStatus(userId, false, new UserCallback() {
    @Override
    public void onSuccess(User user) {
        boolean isActive = user.isActive();
        // Handle updated user status
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
        String lastUpdated = location.getLastUpdated();
        // Use the location data
    }

    @Override
    public void onError(String error) {
        // Handle error
    }
});
```

## Best Practices

1. User Management:
   - Store user IDs securely
   - Track user active status for online/offline functionality
   - Handle user IDs with care
   - Don't expose user IDs in logs

2. Location Updates:
   - Update frequency: 5-10 seconds recommended during active tracking
   - Validate location data before sending
   - Handle location permissions properly
   - Check user active status before updating location

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

Check out our [demo application](https://github.com/OmriRoter/live-location-tracking-sdk/tree/master/app) for a complete implementation example.

## API Documentation

For detailed API documentation and examples, please visit our [Documentation](docs/examples/examples.md).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

For support, please create an issue in the [GitHub repository](https://github.com/OmriRoter/live-location-tracking-sdk/issues).

## Change Log

### Version 1.1.0
- Added user active status tracking
- Added automatic default location initialization
- Added status update endpoint
- Improved error handling
- Updated documentation

## Acknowledgments

- Built on top of [Live Location Tracking API](https://live-location-tracking-backend.vercel.app)
- Uses [Retrofit](https://square.github.io/retrofit/) for API communication
