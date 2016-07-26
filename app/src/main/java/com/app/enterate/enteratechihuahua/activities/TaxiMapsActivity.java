package com.app.enterate.enteratechihuahua.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.app.enterate.enteratechihuahua.callbacks.TaxiSitesLoadedListener;
import com.app.enterate.enteratechihuahua.logging.L;
import com.app.enterate.enteratechihuahua.pojo.TaxiSite;
import com.app.enterate.enteratechihuahua.test.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class TaxiMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        TaxiSitesLoadedListener, LocationListener {

    private GoogleMap mMap;
    private Marker marker;
    private ArrayList<TaxiSite> taxiSites  = new ArrayList<>();
    private LocationManager mLocationManager = null;
    private String provider = null;
    private Marker mCurrentPosition = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        taxiSites = this.getIntent().getParcelableArrayListExtra("taxis");
        System.out.println("oncreatemap"+taxiSites);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(28.6407642, -106.0726933);
        LatLng taxi = new LatLng(28.638502,-106.0734079);
        LatLng taxi2 = new LatLng(28.6371743,-106.0745948);
        LatLng taxi3 = new LatLng(28.6393783,-106.0731112);
        LatLng taxi4 = new LatLng(28.6373318,-106.0775002);
        LatLng chihuahua = new LatLng(28.6148879, -106.015528);


        if (isProviderAvailable() && (provider != null)) {
            locateCurrentPosition();
        }


        // map is a GoogleMap object
        // Add a marker in Sydney and move the camera




        /*marker = mMap.addMarker(new MarkerOptions().position(sydney).title("Taxi")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi)));
        marker = mMap.addMarker(new MarkerOptions().position(taxi).title("Taxi")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi)));
        marker = mMap.addMarker(new MarkerOptions().position(taxi2).title("Taxi")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi)));
        marker = mMap.addMarker(new MarkerOptions().position(taxi3).title("Taxi")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi)));
        marker = mMap.addMarker(new MarkerOptions().position(taxi4).title("Taxi")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi)));*/

        System.out.println("onMAPtaxis"+taxiSites);

        for (TaxiSite t : taxiSites ) {
            LatLng latLng = new LatLng(Double.parseDouble(t.getLatitude()), Double.parseDouble(t.getLongitude()));
            marker = mMap.addMarker(new MarkerOptions().position(latLng).title(t.getName()).snippet(t.getPhone())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi)));
        }


        // Create a LatLngBounds that includes Australia.
        LatLngBounds Chihuahua = new LatLngBounds(
                new LatLng(28.6148879, -106.015528), new LatLng(29, -106));

        // Set the camera to the greatest possible zoom level that includes the
        // bounds
        //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(Chihuahua, 10));

        //mMap.addMarker(new MarkerOptions().position(sydney).title("Taxi"));
        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);

        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(chihuahua));
        /*mMap.addMarker(new MarkerOptions()

                .position(new LatLng(28.639884, -106.0720454))
                .title("Hello world")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.person)));*/

        mMap.setOnMarkerClickListener(this);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(chihuahua));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Chihuahua.getCenter(), 11));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        final Marker marker2 = marker;
        if (marker.equals(marker))
        {
            //handle click here
            /*
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:123456789"));
            startActivity(callIntent);

            Toast.makeText(this, "Llamando",
                    Toast.LENGTH_LONG).show();*/

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Llamar a " + marker.getTitle() + " tiempo aproximado " + (int)(Math.random()*30 + 1) + " minutos:");
            alertDialog.setMessage("Telefono: "+marker.getSnippet().toString());
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Llamar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            callIntent.setData(Uri.parse("tel:"+marker2.getSnippet().toString()));
                            startActivity(callIntent);
                        }
                    });
            alertDialog.show();

            return true;


        }
        return false;
    }

    private void locateCurrentPosition() {

        int status = getPackageManager().checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION,
                getPackageName());

        if (status == PackageManager.PERMISSION_GRANTED) {
            Location location = mLocationManager.getLastKnownLocation(provider);
            updateWithNewLocation(location);
            //  mLocationManager.addGpsStatusListener(this);
            long minTime = 5000;// ms
            float minDist = 5.0f;// meter
            mLocationManager.requestLocationUpdates(provider, minTime, minDist,
                    this);
        }
    }

    private boolean isProviderAvailable() {
        mLocationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        provider = mLocationManager.getBestProvider(criteria, true);
        if (mLocationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;

            return true;
        }

        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
            return true;
        }

        if (provider != null) {
            return true;
        }
        return false;
    }

    private void updateWithNewLocation(Location location) {

        if (location != null && provider != null) {
            double lng = location.getLongitude();
            double lat = location.getLatitude();

            addBoundaryToCurrentPosition(lat, lng);

            CameraPosition camPosition = new CameraPosition.Builder()
                    .target(new LatLng(lat, lng)).zoom(10f).build();

            if (mMap != null)
                mMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(camPosition));
        } else {
            //Log.d("Location error", "Something went wrong");
        }
    }

    private void addBoundaryToCurrentPosition(double lat, double lang) {

        MarkerOptions mMarkerOptions = new MarkerOptions();
        mMarkerOptions.position(new LatLng(lat, lang));
        mMarkerOptions.icon(BitmapDescriptorFactory
                .fromResource(R.drawable.dotmap));
        mMarkerOptions.anchor(0.5f, 0.5f);

        CircleOptions mOptions = new CircleOptions()
                .center(new LatLng(lat, lang)).radius(1000)
                .strokeColor(0x110000FF).strokeWidth(1).fillColor(0x110000FF);
        mMap.addCircle(mOptions);
        if (mCurrentPosition != null)
            mCurrentPosition.remove();
        mCurrentPosition = mMap.addMarker(mMarkerOptions);
    }

    @Override
    public void onTaxiSitesLoaded(ArrayList<TaxiSite> listTaxiSites) {
        L.m("TaxiMapsActivity: onTaxiSiteLoaded");
        taxiSites = listTaxiSites;
        System.out.println("taxis cargados"+taxiSites);
    }

    @Override
    public void onLocationChanged(Location location) {
        updateWithNewLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                break;
            case LocationProvider.AVAILABLE:
                break;
        }
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        updateWithNewLocation(null);
    }
}