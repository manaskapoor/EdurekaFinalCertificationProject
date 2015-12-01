package com.edureka.movies.trendingmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.edureka.movies.adapters.TutorialsAdapter;
import com.edureka.movies.R;
import com.edureka.movies.utils.UtilityConstants;
import com.viewpagerindicator.CirclePageIndicator;

public class MoviesTutorials extends ActionBarActivity {
    SharedPreferences tutorialPreferences;
    SharedPreferences.Editor tutorialPreferencesEditor;
    CirclePageIndicator mIndicator;
    ViewPager vpPager;
    TextView skipHowItWorks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_tutorials);
        getSupportActionBar().hide();
        tutorialPreferences= getApplicationContext().getSharedPreferences(UtilityConstants.PREFERENCES_TUTORIALS, Context.MODE_PRIVATE);
        tutorialPreferencesEditor=tutorialPreferences.edit();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        skipHowItWorks = (TextView)findViewById(R.id.skipHowItWorks);

        final int[] flag = new int[] {R.drawable.homepage,R.drawable.sort, R.drawable.search,
                R.drawable.movie_details, R.drawable.movie_share};

        String[] rank = new String[] { "1", "2", "3", "4","5"};
        String[] middleText = new String[] { "Check all the latest trending movies at one place showing the title of the movie, a thumbnail with a tagline and its IMDB rating.","You can sort the movies depending on the title, rating and released date with utmost ease.", "Searching the movie with its title is also available to ease of the users as to which movie they wants to check.", "You can check the complete overview of the movie here, its released date, its trailer, homepage and many more things on a single page.", "You also have the option to share your favorite movies with your friends with the option of share."};
        vpPager = (ViewPager)findViewById(R.id.pager);
        TutorialsAdapter adapter = new TutorialsAdapter(MoviesTutorials.this,rank,flag,middleText);
        vpPager.setAdapter(adapter);
        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(vpPager);

        final float density = getResources().getDisplayMetrics().density;

        mIndicator.setStrokeWidth(1);
        mIndicator.setRadius(6 * density);

        vpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mIndicator.onPageScrolled(position, 0, 0);
                if (position == (flag.length - 1)) {
                    skipHowItWorks.setText("Start >");
                } else {
                    skipHowItWorks.setText("Skip >");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        skipHowItWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(skipHowItWorks.getText().toString().contains("Start")){
                        tutorialPreferencesEditor.putBoolean(UtilityConstants.PREFERENCES_TUTORIAL,true).commit();
                }
                startActivity(new Intent(MoviesTutorials.this,MoviesList.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies_tutorials, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
