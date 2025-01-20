
---

### `docs/examples/basic_usage.md`

```markdown
# Basic Usage Examples

## Basic Setup
```java
// Initialize the library
LocationTracker tracker = new LocationTrackerImpl();

// Request necessary permissions
if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
    ActivityCompat.requestPermissions(this,
            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
            PERMISSION_REQUEST_CODE);
}
```

## User Creation Example
```java
tracker.createUser("JohnDoe", new UserCallback() {
    @Override
    public void onSuccess(User user) {
        String userId = user.getId();
        // Save userId to SharedPreferences
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_id", userId);
        editor.apply();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();
    }
});
```

## Location Sharing Example
```java
// Start sharing location
String userId = preferences.getString("user_id", "");
locationTracker.updateLocation(userId, latitude, longitude, new LocationCallback() {
    @Override
    public void onSuccess(Location location) {
        Log.d("LocationUpdate", "Location shared successfully");
    }

    @Override
    public void onError(String error) {
        Log.e("LocationUpdate", "Error sharing location: " + error);
    }
});
```
```

---

### `docs/examples/maps_integration.md`

```markdown
# Google Maps Integration Example

## Activity Layout
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <fragment
        android:id="@+id/map"
        android:name="com.google.android.gms.maps.SupportMapFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <Button
        android:id="@+id/startTracking"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:layout_margin="16dp"
        android:text="Start Tracking" />
</RelativeLayout>
```

## Activity Code
```java
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LocationTracker locationTracker;
    private String userId;
    private Marker userMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        
        // Initialize tracker
        locationTracker = new LocationTrackerImpl();
        
        // Get userId from preferences
        userId = getSharedPreferences("app_prefs", MODE_PRIVATE)
                .getString("user_id", "");
                
        // Initialize map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            updateUserLocation();
            handler.postDelayed(this::updateUserLocation, 10000);
        }, 0);
    }

    private void updateUserLocation() {
        locationTracker.getUserLocation(userId, new LocationCallback() {
            @Override
            public void onSuccess(Location location) {
                LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
                
                if (userMarker == null) {
                    userMarker = mMap.addMarker(new MarkerOptions().position(position).title("Current Location"));
                } else {
                    userMarker.setPosition(position);
                }
                
                mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
            }

            @Override
            public void onError(String error) {
                Toast.makeText(MapsActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```
```

---

### `docs/examples/error_handling.md`

```markdown
# Error Handling Examples

## Network Error Handling
```java
locationTracker.updateLocation(userId, latitude, longitude, new LocationCallback() {
    @Override
    public void onSuccess(Location location) {
        errorCount = 0;
    }

    @Override
    public void onError(String error) {
        errorCount++;
        if (errorCount < MAX_RETRY_COUNT) {
            new Handler().postDelayed(() -> {
                updateLocation(userId, latitude, longitude);
            }, RETRY_DELAY_MS);
        } else {
            showErrorDialog("Network error. Please check your connection.");
        }
    }
});
```

## Permission Handling
```java
public class LocationPermissionHelper {
    private static final int PERMISSION_REQUEST_CODE = 123;
    private final Activity activity;

    public LocationPermissionHelper(Activity activity) {
        this.activity = activity;
    }

    public boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(activity, 
                Manifest.permission.ACCESS_FINE_LOCATION) 
                != PackageManager.PERMISSION_GRANTED) {
            
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    public void handlePermissionResult(int requestCode, 
                                     String[] permissions, 
                                     int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                showPermissionExplanationDialog();
            }
        }
    }
}
```
```

