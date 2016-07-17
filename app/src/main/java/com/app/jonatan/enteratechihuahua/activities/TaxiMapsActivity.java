package com.app.jonatan.enteratechihuahua.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.jonatan.enteratechihuahua.callbacks.TaxiSitesLoadedListener;
import com.app.jonatan.enteratechihuahua.logging.L;
import com.app.jonatan.enteratechihuahua.pojo.TaxiSite;
import com.app.jonatan.enteratechihuahua.tasks.TaskLoadTaxiSites;
import com.app.jonatan.enteratechihuahua.test.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class TaxiMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        TaxiSitesLoadedListener {

    private GoogleMap mMap;
    private Marker marker;
    private ArrayList<TaxiSite> taxiSites  = new ArrayList<>();

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
        mMap.addMarker(new MarkerOptions()

                .position(new LatLng(28.639884, -106.0720454))
                .title("Hello world")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.person)));

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
            alertDialog.setTitle("Llamar a " + marker.getTitle() + ":");
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

    @Override
    public void onTaxiSitesLoaded(ArrayList<TaxiSite> listTaxiSites) {
        L.m("TaxiMapsActivity: onTaxiSiteLoaded");
        taxiSites = listTaxiSites;
        System.out.println("taxis cargados"+taxiSites);
    }
}
