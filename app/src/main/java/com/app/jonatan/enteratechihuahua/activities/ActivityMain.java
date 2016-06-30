package com.app.jonatan.enteratechihuahua.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.app.jonatan.enteratechihuahua.adapters.AdapterSubcategory;
import com.app.jonatan.enteratechihuahua.extras.Constants;
import com.app.jonatan.enteratechihuahua.extras.SortListener;
import com.app.jonatan.enteratechihuahua.extras.Subcategory;
import com.app.jonatan.enteratechihuahua.fragments.FragmentBars;
import com.app.jonatan.enteratechihuahua.fragments.FragmentBeauty;
import com.app.jonatan.enteratechihuahua.fragments.FragmentClubs;
import com.app.jonatan.enteratechihuahua.fragments.FragmentCoffee;
import com.app.jonatan.enteratechihuahua.fragments.FragmentEvents;
import com.app.jonatan.enteratechihuahua.fragments.FragmentGym;
import com.app.jonatan.enteratechihuahua.fragments.FragmentPets;
import com.app.jonatan.enteratechihuahua.fragments.FragmentPlazas;
import com.app.jonatan.enteratechihuahua.fragments.FragmentSchools;
import com.app.jonatan.enteratechihuahua.network.VolleySingleton;
import com.app.jonatan.enteratechihuahua.test.R;
import com.daimajia.androidanimations.library.sliders.SlideOutDownAnimator;
import com.github.paolorotolo.appintro.AppIntro;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.app.jonatan.enteratechihuahua.fragments.FragmentNavigationDrawer;
import com.app.jonatan.enteratechihuahua.fragments.FragmentGovernment;
import com.app.jonatan.enteratechihuahua.fragments.FragmentRestaurants;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.List;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import me.tatarka.support.job.JobScheduler;

public class ActivityMain extends ActionBarActivity implements MaterialTabListener, View.OnClickListener {
    public boolean isFirstStart;
    //int representing our 0th tab corresponding to the Fragment where search results are dispalyed
    public static final int TAB_COMIDA = 0;
    //int corresponding to our 1st tab corresponding to the Fragment where box office hits are dispalyed
    public static final int  TAB_BARES= 1;
    //int corresponding to our 2nd tab corresponding to the Fragment where upcoming movies are displayed
    public static final int  TAB_ANTROS= 2;
    //int corresponding to the number of tabs in our Activity
    public static final int TAB_CAFES = 3;

    public static final int TAB_PLAZAS = 4;

    public static final int TAB_EVENTOS = 5;

    public static final int TAB_BELLEZA = 6;

    public static final int TAB_MASCOTAS = 7;

    public static final int TAB_GIMNASIOS = 8;

    public static final int TAB_GUBERNAMENTALES = 9;

    public static final int TAB_ESCUELAS = 10;

    public static final int TAB_COUNT = 11;
    //int corresponding to the id of our JobSchedulerService
    private static final int JOB_ID = 100;
    //tag associated with the FAB menu button that sorts by name
    private static final String TAG_SORT_NAME = "sortName";
    //tag associated with the FAB menu button that sorts by date
    private static final String TAG_SORT_DATE = "sortDate";
    //tag associated with the FAB menu button that sorts by ratings
    private static final String TAG_SORT_RATINGS = "sortRatings";
    //Run the JobSchedulerService every 2 minutes
    private static final long POLL_FREQUENCY = 28800000;
    private JobScheduler mJobScheduler;
    private Toolbar mToolbar;
    //a layout grouping the toolbar and the tabs together
    private ViewGroup mContainerToolbar;
    private MaterialTabHost mTabHost;
    private ViewPager mPager;
    private ViewPagerAdapter mAdapter;
    private FloatingActionButton mFAB;
    private FloatingActionMenu mFABMenu;
    private FragmentNavigationDrawer mDrawerFragment;
    private ImageView mLogo;
    public String url;
    public String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupFAB();
        setupTabs();
        setupDrawer();
        mLogo = (ImageView) findViewById(R.id.custom_title);
        mLogo.setImageResource(R.drawable.enterate);


        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //window.setStatusBarColor(this.getResources().getColor(R.color.black));

        //  Declare a new thread to do a preference check
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    Intent i = new Intent(ActivityMain.this, Intro.class);
                    startActivity(i);

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                }
            }
        });

        // Start the thread
        t.start();

        //AnimationUtils.animateToolbarDroppingDown(mContainerToolbar);

    }


    private void setupDrawer() {
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        mContainerToolbar = (ViewGroup) findViewById(R.id.container_app_bar);
        //set the Toolbar as ActionBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //setup the NavigationDrawer
        mDrawerFragment = (FragmentNavigationDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        mDrawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById
                        (R.id.drawer_layout), mToolbar,getIntent().getExtras().getString("url"),
                getIntent().getExtras().getString("name"));
        String shit;
        shit = getIntent().getExtras().getString("url");
        System.out.println("------------>"+shit);

        //loadImages(getIntent().getExtras().getString("url"));
    }

    private void setupTabs() {
        mTabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        mPager = (ViewPager) findViewById(R.id.viewPager);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        //when the page changes in the ViewPager, update the Tabs accordingly
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTabHost.setSelectedNavigationItem(position);

            }
        });
        //Add all the Tabs to the TabHost
        for (int i = 0; i < mAdapter.getCount(); i++) {
            mTabHost.addTab(
                    mTabHost.newTab()
                            .setIcon(mAdapter.getIcon(i))
                            .setTabListener(this));
        }
    }

    private void setupFAB() {
        /*//define the icon for the main floating action button
        ImageView iconFAB = new ImageView(this);
        iconFAB.setImageResource(R.drawable.ic_action_new);

        //set the appropriate background for the main floating action button along with its icon
        mFAB = new com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.Builder(this)
                .setContentView(iconFAB)
                .setBackgroundDrawable(R.drawable.selector_button_red)
                .build();

        //define the icons for the sub action buttons
        ImageView iconSortName = new ImageView(this);
        iconSortName.setImageResource(R.drawable.ic_action_alphabets);
        ImageView iconSortDate = new ImageView(this);
        iconSortDate.setImageResource(R.drawable.ic_action_calendar);
        ImageView iconSortRatings = new ImageView(this);
        iconSortRatings.setImageResource(R.drawable.ic_action_important);

        //set the background for all the sub buttons
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_sub_button_gray));


        //build the sub buttons
        SubActionButton buttonSortName = itemBuilder.setContentView(iconSortName).build();
        SubActionButton buttonSortDate = itemBuilder.setContentView(iconSortDate).build();
        SubActionButton buttonSortRatings = itemBuilder.setContentView(iconSortRatings).build();

        //to determine which button was clicked, set Tags on each button
        buttonSortName.setTag(TAG_SORT_NAME);
        buttonSortDate.setTag(TAG_SORT_DATE);
        buttonSortRatings.setTag(TAG_SORT_RATINGS);

        buttonSortName.setOnClickListener(this);
        buttonSortDate.setOnClickListener(this);
        buttonSortRatings.setOnClickListener(this);

        //add the sub buttons to the main floating action button
        mFABMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonSortName)
                .addSubActionView(buttonSortDate)
                .addSubActionView(buttonSortRatings)
                .attachTo(mFAB)
                .build();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        mPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    @Override
    public void onClick(View v) {

        Fragment fragment = (Fragment) mAdapter.instantiateItem(mPager, mPager.getCurrentItem());
        if (fragment instanceof SortListener) {

            if (v.getTag().equals(TAG_SORT_NAME)) {
                //call the sort by name method on any Fragment that implements sortlistener
                ((SortListener) fragment).onSortByName();
            }
            if (v.getTag().equals(TAG_SORT_DATE)) {
                //call the sort by date method on any Fragment that implements sortlistener
                ((SortListener) fragment).onSortByDate();
            }
            if (v.getTag().equals(TAG_SORT_RATINGS)) {
                //call the sort by ratings method on any Fragment that implements sortlistener
                ((SortListener) fragment).onSortByRating();
            }
        }
    }

    public void onDrawerSlide(float slideOffset) {
        toggleTranslateFAB(slideOffset);
    }

    public void onDrawerItemClicked(int index) {
        mPager.setCurrentItem(index);
    }

    private void toggleTranslateFAB(float slideOffset) {
        if (mFABMenu != null) {
            if (mFABMenu.isOpen()) {
                mFABMenu.close(true);
            }
            mFAB.setTranslationX(slideOffset * 200);
        }
    }


    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        int icons[] = {R.drawable.ic_action_restaurant, R.drawable.ic_action_event,
                R.drawable.ic_action_alphabets, R.drawable.ic_action_alphabets,
                R.drawable.ic_action_alphabets, R.drawable.ic_action_alphabets,
                R.drawable.ic_action_alphabets, R.drawable.ic_action_alphabets,
                R.drawable.ic_action_alphabets, R.drawable.ic_action_alphabets,
                R.drawable.ic_action_alphabets,
        };

        FragmentManager fragmentManager;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentManager = fm;
        }

        public Fragment getItem(int num) {
            Fragment fragment = null;
//            L.m("getItem called for " + num);
            switch (num) {
                case TAB_COMIDA:
                    fragment = FragmentRestaurants.newInstance("", "");
                    break;
                case TAB_BARES:
                    fragment = FragmentBars.newInstance("", "");
                    break;
                case TAB_ANTROS:
                    fragment = FragmentClubs.newInstance("", "");
                    break;
                case TAB_CAFES:
                    fragment = FragmentCoffee.newInstance("", "");
                    break;
                case TAB_PLAZAS:
                    fragment = FragmentPlazas.newInstance("", "");
                    break;
                case TAB_EVENTOS:
                    fragment = FragmentEvents.newInstance("", "");
                    break;
                case TAB_BELLEZA:
                    fragment = FragmentBeauty.newInstance("", "");
                    break;
                case TAB_MASCOTAS:
                    fragment = FragmentPets.newInstance("", "");
                    break;
                case TAB_GIMNASIOS:
                    fragment = FragmentGym.newInstance("", "");
                    break;
                case TAB_GUBERNAMENTALES:
                    fragment = FragmentGovernment.newInstance("", "");
                    break;
                case TAB_ESCUELAS:
                    fragment = FragmentSchools.newInstance("", "");
                    break;
            }
            return fragment;

        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabs)[position];
        }

        private Drawable getIcon(int position) {
            return getResources().getDrawable(icons[position]);
        }
    }


}
