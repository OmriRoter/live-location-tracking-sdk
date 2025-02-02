# Live Location Tracking Android Library

[![](https://jitpack.io/v/OmriRoter/live-location-tracking-sdk.svg)](https://jitpack.io/#OmriRoter/live-location-tracking-sdk)

A lightweight Android library for real-time location tracking, designed for easy integration with the Live Location Tracking API.

## Features

- User Management:
  - Create new users with unique usernames
  - Verify existing users using their user ID
  - Update and track user active status
- Location Services:
  - Update user location in real-time
  - Retrieve locations of active users
  - Built-in coordinate validation
- Error Handling:
  - Comprehensive error reporting
  - Network error handling
  - Input validation
- Built with Retrofit for reliable API communication

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
    implementation("com.github.OmriRoter:live-location-tracking-sdk:1.2.3")
}
```

## Core Components

### User Management

#### Creating a New User
Create a new user account with a unique username:
```java
LocationTracker tracker = new LocationTrackerImpl();

tracker.createUser("username", new UserCallback() {
    @Override
    public void onSuccess(User user) {
        // IMPORTANT: Store this ID securely - it's required for future login
        String userId = user.getId();
        String username = user.getUsername();
        boolean isActive = user.isActive(); // true by default
        String createdAt = user.getCreatedAt();
    }

    @Override
    public void onError(String error) {
        // Handle specific error cases:
        // - "Username already exists"
        // - "Username cannot be null or empty"
        // - "Username must be at least 3 characters long"
        // - "Username cannot be longer than 50 characters"
    }
});
```

#### Verifying an Existing User (Login)
Verify a user exists using their user ID:
```java
tracker.verifyUser(userId, new UserCallback() {
    @Override
    public void onSuccess(User user) {
        // User verified successfully
        boolean isActive = user.isActive();
        String username = user.getUsername();
    }

    @Override
    public void onError(String error) {
        // Handle specific error cases:
        // - "User not found"
        // - "Invalid user ID format"
    }
});
```

#### Managing User Status
Update user's active status:
```java
tracker.updateUserStatus(userId, true, new UserCallback() {
    @Override
    public void onSuccess(User user) {
        // Status updated successfully
        boolean currentStatus = user.isActive();
    }

    @Override
    public void onError(String error) {
        // Handle specific error cases:
        // - "User not found"
        // - "Invalid user ID format"
    }
});
```

### Location Services

#### Updating User Location
Update the current location of a user:
```java
tracker.updateLocation(userId, latitude, longitude, new LocationCallback() {
    @Override
    public void onSuccess(Location location) {
        double updatedLat = location.getLatitude();
        double updatedLng = location.getLongitude();
        String lastUpdated = location.getLastUpdated();
    }

    @Override
    public void onError(String error) {
        // Handle specific error cases:
        // - "User not found"
        // - "Invalid user ID format"
        // - "Invalid latitude value" (must be between -90 and 90)
        // - "Invalid longitude value" (must be between -180 and 180)
    }
});
```

#### Retrieving User Location
Get the latest location of an active user:
```java
tracker.getUserLocation(userId, new LocationCallback() {
    @Override
    public void onSuccess(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        String lastUpdated = location.getLastUpdated();
    }

    @Override
    public void onError(String error) {
        // Handle specific error cases:
        // - "User not found"
        // - "User is not active"
        // - "Location not found for this user"
        // - "Invalid user ID format"
    }
});
```

## Implementation Guide

### Step 1: Setup
1. Add required permissions to AndroidManifest.xml:
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

2. Initialize the LocationTracker:
```java
LocationTracker tracker = new LocationTrackerImpl();
```

### Step 2: User Flow Implementation
1. Registration:
```java
// When user wants to register
tracker.createUser(username, new UserCallback() {
    @Override
    public void onSuccess(User user) {
        // CRUCIAL: Store user.getId() securely
        // You'll need this ID for future sessions
    }
    
    @Override
    public void onError(String error) {
        // Show error to user
    }
});
```

2. Login:
```java
// When user wants to login using stored userId
tracker.verifyUser(storedUserId, new UserCallback() {
    @Override
    public void onSuccess(User user) {
        // User verified - proceed to main functionality
    }
    
    @Override
    public void onError(String error) {
        // Handle invalid/expired userId
    }
});
```

### Step 3: Location Tracking Implementation
1. Update user's location:
```java
// Call this periodically (e.g., every 5-10 seconds)
tracker.updateLocation(userId, currentLatitude, currentLongitude, 
    new LocationCallback() {
        @Override
        public void onSuccess(Location location) {
            // Location updated successfully
        }
        
        @Override
        public void onError(String error) {
            // Handle update failure
        }
    });
```

2. Track another user's location:
```java
// Call this to get another user's location
tracker.getUserLocation(targetUserId, new LocationCallback() {
    @Override
    public void onSuccess(Location location) {
        // Update UI with location.getLatitude() and location.getLongitude()
    }
    
    @Override
    public void onError(String error) {
        if (error.contains("not active")) {
            // User is not sharing their location
        } else {
            // Handle other errors
        }
    }
});
```

## Best Practices

### User Management
1. **User ID Storage**
   - Store user ID securely (e.g., EncryptedSharedPreferences)
   - Never expose user ID in logs or UI
   - Validate user ID before making API calls

2. **Status Management**
   - Update status to false when user wants to stop sharing location
   - Check user status before attempting to get their location
   - Handle "User not active" errors appropriately

### Location Updates
1. **Update Frequency**
   - Recommended: 5-10 seconds between updates
   - Adjust based on your specific use case
   - Consider battery impact

2. **Error Handling**
   - Always implement both success and error callbacks
   - Show user-friendly error messages
   - Handle network errors gracefully

## Error Handling Guide

### Common Error Cases
1. User Creation Errors:
   - "Username already exists"
   - "Username cannot be null or empty"
   - "Username must be at least 3 characters long"
   - "Username cannot be longer than 50 characters"

2. User Verification Errors:
   - "User not found"
   - "Invalid user ID format"

3. Location Update Errors:
   - "Invalid latitude value"
   - "Invalid longitude value"
   - "User not found"

4. Location Retrieval Errors:
   - "User is not active"
   - "Location not found for this user"
   - "Invalid user ID format"

### Example Error Handling
```java
@Override
public void onError(String error) {
    if (error.contains("not active")) {
        // User has disabled location sharing
        showLocationSharingDisabledMessage();
    } else if (error.contains("not found")) {
        // User doesn't exist
        handleInvalidUser();
    } else if (error.contains("Invalid")) {
        // Invalid input
        handleInvalidInput(error);
    } else {
        // Network or server error
        handleGeneralError(error);
    }
}
```

## Version History

### Version 1.1.4 (Current)
- Added user verification endpoint
- Enhanced error handling and validation
- Updated documentation
- Added comprehensive test coverage

## Support

For support and bug reports, please create an issue in the [GitHub repository](https://github.com/OmriRoter/live-location-tracking-sdk/issues).

## License

This project is licensed under the MIT License.
