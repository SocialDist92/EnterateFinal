package com.app.enterate.enteratechihuahua.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.app.enterate.enteratechihuahua.test.R;
import com.github.paolorotolo.appintro.AppIntro;

public class Intro extends AppIntro {
    // Please DO NOT override onCreate. Use init
    @Override
    public void init(Bundle savedInstanceState) {

        // Add your slide's fragments here
        // AppIntro will automatically generate the dots indicator and buttons

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest
        //Here we are adding the four slides
        addSlide(SampleSlide.newInstance(R.layout.intro));
        //addSlide(SampleSlide.newInstance(R.layout.intro2));
        //addSlide(SampleSlide.newInstance(R.layout.intro3));
        //addSlide(SampleSlide.newInstance(R.layout.intro4));

        // Edit the color of the nav bar on Lollipop+ devices
        // setNavBarColor(Color.parseColor("#3F51B5"));

        // Hide Skip/Done button
        showSkipButton(true);
        showStatusBar(false);
        // Turn vibration on and set intensity
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest
        setVibrate(true);
        setVibrateIntensity(30);
        setDepthAnimation();
        // Animations -- use only one of the below. Using both could cause errors.
        //setFadeAnimation(); // OR
/*            setZoomAnimation();
            setFlowAnimation(); // OR
            setSlideOverAnimation(); // OR
            setDepthAnimation(); // OR
            setCustomTransformer(yourCustomTransformer);*/

        // Permissions -- takes a permission and slide number
        //askForPermissions(new String[]{Manifest.permission.CAMERA}, 3);
    }

    @Override
    public void onSkipPressed() {
        // Do something when users tap on Skip button.
        Toast.makeText(getApplicationContext(),
                "LKJS", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ActivityMain.class);
        startActivity(intent);
    }

    @Override
    public void onNextPressed() {
        // Do something when users tap on Next button.
    }

    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.
        finish();
    }

    @Override
    public void onSlideChanged() {
        // Do something when slide is changed
    }
}