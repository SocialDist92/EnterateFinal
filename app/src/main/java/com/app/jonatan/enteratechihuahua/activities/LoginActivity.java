package com.app.jonatan.enteratechihuahua.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.app.jonatan.enteratechihuahua.extras.Constants;
import com.app.jonatan.enteratechihuahua.fragments.FragmentNavigationDrawer;
import com.app.jonatan.enteratechihuahua.network.VolleySingleton;
import com.app.jonatan.enteratechihuahua.test.R;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {


    // Create, automatically open (if applicable), save, and restore the
    // Active Session in a way that is similar to Android UI lifecycles.
    private UiLifecycleHelper uiHelper;
    private View otherView;
    private VolleySingleton mVolleySingleton;
    private ImageLoader mImageLoader;
    private ImageView profile;
    private Bitmap bitmapUser;
    private Button btn;

    private String url;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set View that should be visible after log-in invisible initially
        otherView = (View) findViewById(R.id.other_views);
        otherView.setVisibility(View.GONE);
        btn = (Button) findViewById(R.id.btn);
        // To maintain FB Login session
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        mVolleySingleton = VolleySingleton.getInstance();
        mImageLoader = mVolleySingleton.getImageLoader();
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //window.setStatusBarColor(this.getResources().getColor(R.color.black));

        try{
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.app.jonatan.enteratechihuahua.test", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:",Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ActivityMain.class);
                intent.putExtra("name", ""); //Optional parameters
                intent.putExtra("url", ""); //Optional parameters
                LoginActivity.this.startActivity(intent);

            }
        });

    }

    // Called when session changes
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    // When session is changed, this method is called from callback method
    private void onSessionStateChange(Session session, SessionState state,
                                      Exception exception) {
        final TextView name = (TextView) findViewById(R.id.name);

        // When Session is successfully opened (User logged-in)
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
            // make request to the /me API to get Graph user
            Request.newMeRequest(session, new Request.GraphUserCallback() {

                // callback after Graph API response with user
                // object
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {
                        // Set view visibility to true
                        otherView.setVisibility(View.VISIBLE);
                        // Set User name
                        //name.setText("Hello " + user.getName());
                        System.out.println("--->"+user.getInnerJSONObject());

                        Intent myIntent = new Intent(LoginActivity.this, ActivityMain.class);
                        url = "https://graph.facebook.com/" + user.getId() + "/picture?type=large";

                        loadImages(url);


                        myIntent.putExtra("name", user.getName()); //Optional parameters
                        myIntent.putExtra("url", url); //Optional parameters



                        LoginActivity.this.startActivity(myIntent);



                        // Set Gender
                            /*
                            gender.setText("Your Gender: "
                                + user.getProperty("gender").toString());

                            location.setText("Your Current Location: "
                                + user.getLocation().getProperty("name")
                                .toString());
                                */

                    }
                }
            }).executeAsync();
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
            otherView.setVisibility(View.GONE);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uiHelper.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "OnActivityResult...");
    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }



    private void loadImages(String urlThumbnail) {
        if (!urlThumbnail.equals(Constants.NA)) {
            mImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    bitmapUser= response.getBitmap();
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error);
                }
            });
        }
    }


}
