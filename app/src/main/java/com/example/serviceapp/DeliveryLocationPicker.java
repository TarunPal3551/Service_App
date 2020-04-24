package com.example.serviceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DeliveryLocationPicker extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownsLocation;
    private LocationCallback locationCallback;
    private final float DEFAULT_ZOOM = 18;
    Geocoder geocoder;
    SupportMapFragment mapFragment;
    Context mContext;
    Marker marker_Main;
    MarkerOptions markerOptions;
    FloatingActionButton floatingActionButton;
    List<Address> addresses = new ArrayList<>();
    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private boolean mLocationPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_location_picker);
        mContext = DeliveryLocationPicker.this;
        getLocationPermission();
        if (mLocationPermissionGranted){
            mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
        }

        floatingActionButton = (FloatingActionButton) findViewById(R.id.myLocationButton);
        floatingActionButton.setVisibility(View.GONE);
    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults)
    {
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                    mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
                    //initialize our map
                }
        }
    }

    private void getLocationPermission() {

        String[] permissions = {FINE_LOCATION, COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                markerOptions = new MarkerOptions().position(latLng).draggable(true);
                mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
                getAddressUsingLatLng(latLng);


            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {


            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                mMap.clear();
                mMap.moveCamera
                        (CameraUpdateFactory.newLatLngZoom(marker.getPosition(), DEFAULT_ZOOM));

                markerOptions = new MarkerOptions()
                        .position(marker.getPosition())
                        .draggable(true);
                mMap.addMarker(markerOptions);
                if (marker.getPosition() != null) {
                    getAddressUsingLatLng(marker.getPosition());
                }


            }
        });


//        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
//            View locationButton = ((View) mapFragment.getView().findViewById(2));
//            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
//            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
//            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//            rlp.setMargins(0, 0, 30, 30);
//            locationButton.setBackgroundResource(R.drawable.ic_locationpin);
//        }
        //Check if GPS is ON/OFF ..............................

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient settingsClient = LocationServices.getSettingsClient(mContext);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getDeviceLocation();
            }
        });
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                    try {
                        resolvableApiException.startResolutionForResult(DeliveryLocationPicker.this, 40);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        floatingActionButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    mLastKnownsLocation = task.getResult();
                    if (mLastKnownsLocation != null) {
                        LatLng latLng = new LatLng(mLastKnownsLocation.getLatitude(), mLastKnownsLocation.getLongitude());
                        mMap.clear();
                        mMap.moveCamera
                                (CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
//                       marker_Main=new Marker(mMap.)
//                        marker_Main.setPosition(latLng);

                        markerOptions = new MarkerOptions()
                                .position(latLng)
                                .draggable(true);
                        mMap.addMarker(markerOptions);
                        // getAddressUsingLatLng(new LatLng(mLastKnownsLocation.getLatitude(), mLastKnownsLocation.getLongitude()));


                        if (mLastKnownsLocation != null) {
                            geocoder = new Geocoder(mContext, Locale.getDefault());
                            getAddressUsingLatLng(new LatLng(mLastKnownsLocation.getLatitude(), mLastKnownsLocation.getLongitude()));

                        }


                    } else {
                        final LocationRequest locationRequest = LocationRequest.create();
                        locationRequest.setInterval(10000);
                        locationRequest.setFastestInterval(10000);
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                if (locationResult == null) {
                                    return;
                                }
                                mLastKnownsLocation = locationResult.getLastLocation();
                                LatLng latLng = new LatLng(mLastKnownsLocation.getLatitude(), mLastKnownsLocation.getLongitude());
                                //marker_Main.setPosition(latLng);
                                mMap.clear();
                                markerOptions = new MarkerOptions()
                                        .position(latLng)
                                        .draggable(true);
                                mMap.addMarker(markerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
                                mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                if (mLastKnownsLocation != null) {
                                    geocoder = new Geocoder(mContext, Locale.getDefault());

                                    getAddressUsingLatLng(new LatLng(mLastKnownsLocation.getLatitude(), mLastKnownsLocation.getLongitude()));

                                }

                            }
                        };
                        mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                } else {
                    Toast.makeText(mContext, "Unable to get location.....", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void getAddressUsingLatLng(LatLng latLng) {
        if (mLastKnownsLocation != null) {
            geocoder = new Geocoder(mContext, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                String address_line = addresses.get(0).getAddressLine(0);
                // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();

                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String landmark = addresses.get(0).getLocality();



//                saveDefaultAddresss(address, address_line, city,
//                        postalCode, landmark, String.valueOf(mLastKnownsLocation.getLatitude()), String.valueOf(mLastKnownsLocation.getLongitude()), "Home", 1);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }

}
