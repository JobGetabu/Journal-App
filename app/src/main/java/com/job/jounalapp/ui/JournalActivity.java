package com.job.jounalapp.ui;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.job.jounalapp.R;
import com.job.jounalapp.util.BottomBarAdapter;
import com.job.jounalapp.util.NoSwipePager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JournalActivity extends AppCompatActivity {

    @BindView(R.id.mainactivity_noswipepager)
    NoSwipePager mNoSwipePager;
    @BindView(R.id.mainactivity_bottom_navigation)
    AHBottomNavigation mBottomNavigation;


    public static final String TAG = "MainActivity";


    private FirebaseAuth mAuth;

    private BottomBarAdapter pagerAdapter;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            sendToLogin();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        ButterKnife.bind(this);

        //init
        //firebase
        mAuth = FirebaseAuth.getInstance();
        authListner();

        //bottom nav items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(fetchString(R.string.bottomnav_title_1),
                fetchDrawable(R.drawable.ic_home));
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(fetchString(R.string.bottomnav_title_2),
                fetchDrawable(R.drawable.ic_person));

        mBottomNavigation.addItem(item1);
        mBottomNavigation.addItem(item2);

        mBottomNavigation.setOnTabSelectedListener(onTabSelectedListener);

        mBottomNavigation.setDefaultBackgroundColor(Color.WHITE);
        mBottomNavigation.setAccentColor(fetchColor(R.color.colorPrimary));
        mBottomNavigation.setInactiveColor(fetchColor(R.color.bottomtab_item_resting));

        mBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        //Quick Return Animation
        mBottomNavigation.setBehaviorTranslationEnabled(true);

        mNoSwipePager.setPagingEnabled(false);

        //caches data in fragments
        mNoSwipePager.setOffscreenPageLimit(3);

        pagerAdapter = new BottomBarAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments(new HomeFragment());
        pagerAdapter.addFragments(new ProfileFragment());

        mNoSwipePager.setAdapter(pagerAdapter);

        mBottomNavigation.setCurrentItem(0);
        mNoSwipePager.setCurrentItem(0);

    }

    private Drawable fetchDrawable(@DrawableRes int mdrawable) {
        // Facade Design Pattern
        return ContextCompat.getDrawable(this, mdrawable);
    }

    private String fetchString(@StringRes int mystring) {
        // Facade Design Pattern
        return getResources().getString(mystring);
    }

    private int fetchColor(@ColorRes int color) {
        // Facade Design Pattern
        return ContextCompat.getColor(this, color);
    }

    private void sendToLogin() {
       /* Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();*/
    }

    AHBottomNavigation.OnTabSelectedListener onTabSelectedListener = new AHBottomNavigation.OnTabSelectedListener() {
        @Override
        public boolean onTabSelected(int position, boolean wasSelected) {

            //change fragments
            if (!wasSelected) {
                mNoSwipePager.setCurrentItem(position);
                Log.d(TAG, "onTabSelected: AT :" + position);
            }

            return true;
        }
    };

    //auth state listener for live changes
    private void authListner() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // Sign in logic here.
                    sendToLogin();
                }
            }
        };
    }
}
