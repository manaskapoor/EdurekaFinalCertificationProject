package com.edureka.movies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.edureka.movies.trendingmovies.MoviesList;
import com.edureka.movies.trendingmovies.MoviesTutorials;
import com.edureka.movies.utils.UtilityConstants;


public class SplashScreen extends ActionBarActivity {
    SharedPreferences tutorialPreferences;
    SharedPreferences.Editor tutorialPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        tutorialPreferences= getApplicationContext().getSharedPreferences(UtilityConstants.PREFERENCES_TUTORIALS, Context.MODE_PRIVATE);
        tutorialPreferencesEditor=tutorialPreferences.edit();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(tutorialPreferences.getBoolean(UtilityConstants.PREFERENCES_TUTORIAL,false)){
                    startActivity(new Intent(SplashScreen.this, MoviesList.class));
                }else {
                    startActivity(new Intent(SplashScreen.this, MoviesTutorials.class));
                }
            }
        },UtilityConstants.SPLASH_SCREEN_TIMEOUT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
