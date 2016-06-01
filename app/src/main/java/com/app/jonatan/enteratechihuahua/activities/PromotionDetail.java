package com.app.jonatan.enteratechihuahua.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.app.jonatan.enteratechihuahua.pojo.Promotion;
import com.app.jonatan.enteratechihuahua.pojo.Ubication;
import com.app.jonatan.enteratechihuahua.test.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PromotionDetail extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Promotion mPromotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_detail);
        mPromotion = getIntent().getParcelableExtra("promotion");

        //Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        //setSupportActionBar(toolbar);

        ImageView imgFacebook = (ImageView) findViewById(R.id.facebook);

        imgFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.facebook.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
        }

        /*CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mPromotion.getDescription());
        }*/


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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);

        if (mPromotion.getPlace().getUbications().size() != 0) {

            for(Ubication u: mPromotion.getPlace().getUbications()) {
                if (!u.getLatitud().equals("NA")) {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(u.getLatitud()),
                            Double.parseDouble(u.getLongitude()))).title(mPromotion.getPlace().getName()));
                }
            }
                final Ubication ubication = mPromotion.getPlace().getUbications().get(0);
                final LatLng first = new LatLng(Double.parseDouble(ubication.getLatitud()), Double.parseDouble(ubication.getLongitude()));
                mMap.addMarker(new MarkerOptions().position(first).title("Marker in Starbucks"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(first));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            //NavUtils.navigateUpTo(this, new Intent(this, PlaceListActivity.class));
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
