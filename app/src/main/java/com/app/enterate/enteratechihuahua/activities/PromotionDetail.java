package com.app.enterate.enteratechihuahua.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.app.enterate.enteratechihuahua.extras.Constants;
import com.app.enterate.enteratechihuahua.network.VolleySingleton;
import com.app.enterate.enteratechihuahua.pojo.Promotion;
import com.app.enterate.enteratechihuahua.pojo.Ubication;
import com.app.enterate.enteratechihuahua.test.MyApplication;
import com.app.enterate.enteratechihuahua.test.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PromotionDetail extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Promotion mPromotion;
    private VolleySingleton mVolleySingleton;
    private ImageLoader mImageLoader;
    private ImageView placeLogo;
    private Marker marker;
    private Marker marker1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_detail);
        mPromotion = getIntent().getParcelableExtra("promotion");
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //window.setStatusBarColor(this.getResources().getColor(R.color.black));

        //Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        //setSupportActionBar(toolbar);

        ImageView imgFacebook = (ImageView) findViewById(R.id.facebook);
        ImageView imgTwitter = (ImageView) findViewById(R.id.twitter);
        ImageView imgInstagram = (ImageView) findViewById(R.id.instagram);
        ImageView imgPinterest = (ImageView) findViewById(R.id.pinterest);
        ImageView imgWeb = (ImageView) findViewById(R.id.web);
        TextView place = (TextView) findViewById(R.id.place);
        TextView description = (TextView) findViewById(R.id.description);
        placeLogo = (ImageView) findViewById(R.id.placeImg);

        mVolleySingleton = VolleySingleton.getInstance();
        mImageLoader = mVolleySingleton.getImageLoader();

        place.setText(mPromotion.getPlace().getName());
        description.setText(mPromotion.getDescription());

        description.setMovementMethod(new ScrollingMovementMethod());

        loadImages(mPromotion.getPlace().getUrlImageLogo());

        //mMap.setOnMarkerClickListener(this);



        imgFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mPromotion.getPlace().getUrlFacebook().length() != 0) {
                    Uri uri = Uri.parse(mPromotion.getPlace().getUrlFacebook()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(MyApplication.getAppContext(), "No hay Facebook registrado",  Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        imgTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPromotion.getPlace().getUrlTwitter().length() != 0) {
                    Uri uri = Uri.parse(mPromotion.getPlace().getUrlTwitter()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(MyApplication.getAppContext(), "No hay Twitter registrado",  Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        imgInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPromotion.getPlace().getUrlInstagram().length() != 0) {
                    Uri uri = Uri.parse(mPromotion.getPlace().getUrlInstagram()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(MyApplication.getAppContext(), "No hay Instagram registrado",  Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        imgPinterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPromotion.getPlace().getUrlPinterest().length() != 0) {
                    Uri uri = Uri.parse(mPromotion.getPlace().getUrlPinterest()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(MyApplication.getAppContext(), "No hay Pinterest registrado",  Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        imgWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPromotion.getPlace().getUrlWebPage().length() != 0) {
                    Uri uri = Uri.parse(mPromotion.getPlace().getUrlWebPage()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(MyApplication.getAppContext(), "No hay página registrada",  Toast.LENGTH_SHORT);
                    toast.show();
                }

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
        LatLng sydney = new LatLng(28.6407642, -106.0726933);
        LatLng chihuahua = new LatLng(28.6148879, -106.015528);

        // map is a GoogleMap object
        // Add a marker in Sydney and move the camera


        // Create a LatLngBounds that includes Australia.
        LatLngBounds Chihuahua = new LatLngBounds(
                new LatLng(28.6148879, -106.015528), new LatLng(29, -106));

        // Set the camera to the greatest possible zoom level that includes the
        // bounds
        //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(Chihuahua, 10));

        //mMap.addMarker(new MarkerOptions().position(sydney).title("Taxi"));
        if (mPromotion.getPlace().getUbications().size() != 0) {

            for(Ubication u: mPromotion.getPlace().getUbications()) {
                if (!u.getLatitud().equals("NA")) {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(u.getLatitud()),
                            Double.parseDouble(u.getLongitude()))).title(mPromotion.getPlace().getName()));
                }
            }
                final Ubication ubication = mPromotion.getPlace().getUbications().get(0);
                final LatLng first = new LatLng(Double.parseDouble(ubication.getLatitud()), Double.parseDouble(ubication.getLongitude()));
                //mMap.addMarker(new MarkerOptions().position(first).title("Marker in Starbucks"));
               // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
               mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Chihuahua.getCenter(), 11));
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

    private void loadImages(String urlThumbnail) {
        if (!urlThumbnail.equals(Constants.NA)) {
            mImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    placeLogo.setImageBitmap(response.getBitmap());

                }

                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
        }
    }
}
