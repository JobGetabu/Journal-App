package com.job.jounalapp.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private FirebaseAuth mAuth;

    private BottomBarAdapter pagerAdapter;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onStart() {
        super.onStart();
        shouldSignIn();
    }

    private void shouldSignIn() {
        if (mAuth.getCurrentUser() != null) {
            // already signed in
            Toast.makeText(this, "Signed in as " + mAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_LONG).show();
        } else {
            // not signed in
            Toast.makeText(this, "Not Signed in", Toast.LENGTH_SHORT).show();
            sendToLogin();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

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
        mNoSwipePager.setOffscreenPageLimit(1);

        pagerAdapter = new BottomBarAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments(new HomeFragment());
        pagerAdapter.addFragments(new ProfileFragment());

        mNoSwipePager.setAdapter(pagerAdapter);
        mBottomNavigation.setCurrentItem(0);
        mNoSwipePager.setCurrentItem(0);

    }

    //get drawables #Facade Design Pattern
    private Drawable fetchDrawable(@DrawableRes int mdrawable) {
        return ContextCompat.getDrawable(this, mdrawable);
    }

    //get strings #Facade Design Pattern
    private String fetchString(@StringRes int mystring) {
        // Facade Design Pattern
        return getResources().getString(mystring);
    }

    //get colors #Facade Design Pattern
    private int fetchColor(@ColorRes int color) {
        // Facade Design Pattern
        return ContextCompat.getColor(this, color);
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

    private void sendToLogin() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_logout:
                Toast.makeText(JournalActivity.this, "Signing you out", Toast.LENGTH_SHORT).show();
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                                sendToLogin();
                            }
                        });

                break;
        }
        return true;
    }
}
