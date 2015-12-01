package com.edureka.movies.trendingmovies;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edureka.movies.adapters.OverviewText;
import com.edureka.movies.R;
import com.edureka.movies.pojoclasses.TrendingMovies;
import com.edureka.movies.utils.UtilityConstants;
import com.edureka.movies.utils.UtilityFunctions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieHomePageDisplay extends ActionBarActivity {

    TextView tvIdBookAuthor;
    TextView tvIdBookPrice;
    TextView movieCertificate,movieRating,movieLength;
    TextView movieGenre;
    Button movieTrailer,movieHomepage;
    ImageView ivIdBookCover;
    OverviewText expandableTextView;
    ImageButton shareButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_home_page_display);
        getSupportActionBar().hide();
        tvIdBookAuthor = (TextView)findViewById(R.id.movieTitleMain);
        tvIdBookPrice = (TextView)findViewById(R.id.movieReleaseDateMain);
        movieCertificate= (TextView)findViewById(R.id.movieCertificationMain);
        movieRating= (TextView)findViewById(R.id.movieRatingMain);
        movieLength= (TextView)findViewById(R.id.movieLengthMain);
        movieGenre= (TextView)findViewById(R.id.movieGeneresMain);
        movieTrailer = (Button)findViewById(R.id.movieTrailer);
        movieHomepage = (Button)findViewById(R.id.movieHomePage);
        expandableTextView = (OverviewText) findViewById(R.id.movieOverview);
        ivIdBookCover = (ImageView)findViewById(R.id.movieImageMain);
        shareButton = (ImageButton)findViewById(R.id.shareButton);
        Bundle b = getIntent().getExtras();
        TrendingMovies p = (TrendingMovies) b.getParcelable(UtilityConstants.MOVIE);
        final String shareMessage = UtilityConstants.SHARE_MESSAGE + p.getTitle();
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareClick(shareMessage);
            }
        });
        tvIdBookAuthor.setText(p.getTitle() + " (" + p.getYear() + ") ");
        tvIdBookPrice.setText(UtilityFunctions.formatReleaseDate(p.getReleased()));
        if(p.getCertificate().equalsIgnoreCase("null") || p.getCertificate().isEmpty()){
            movieCertificate.setText("NA");
        }else {
            movieCertificate.setText(p.getCertificate());
        }
        movieRating.setText(UtilityFunctions.round(p.getRating(), 2)+"/10");
        movieLength.setText(UtilityFunctions.movieTime(p.getRuntime()));

        movieGenre.setText(UtilityFunctions.getAllGenres(p.getGenre()));
        expandableTextView.setText(p.getOverview());
        Picasso.with(getApplicationContext())
                .load(p.getBannerImage())
                .placeholder(R.drawable.progress_image_animation)
                .error(R.drawable.noimageavailablemain)
                .into(ivIdBookCover);

        final ProgressDialog progressDialog= new ProgressDialog(MovieHomePageDisplay.this);
        final String imageCover=   p.getFullPoster();

        ivIdBookCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UtilityFunctions.isInternetOn(MovieHomePageDisplay.this)) {
                    progressDialog.setMessage(UtilityConstants.LOADER_MESSAGE);
                    progressDialog.show();
                    showImage(imageCover, progressDialog);
                }else{
                    Toast.makeText(getApplicationContext(),UtilityConstants.NETWORK_ERROR,Toast.LENGTH_SHORT).show();
                }
            }
        });

        final String trailerUrl=   p.getTrailer();
        movieTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(trailerUrl.equalsIgnoreCase("null")){
                    Toast.makeText(getApplicationContext(),UtilityConstants.NO_TRAILERS,Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    } catch (ActivityNotFoundException e) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                        startActivity(i);
                    }
                }
            }
        });


        final String homePageUrl=   p.getHomepage();
        movieHomepage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(homePageUrl.equalsIgnoreCase("null")){
                        Toast.makeText(getApplicationContext(),UtilityConstants.NO_HOMEPAGE,Toast.LENGTH_SHORT).show();
                    }else {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(homePageUrl));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        } catch (ActivityNotFoundException e) {
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(homePageUrl));
                            startActivity(i);
                        }
                    }

                }
            });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_home_page_display, menu);
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


    public void showImage(String imageCover,ProgressDialog pd) {
        Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(this);
        Picasso.with(getApplicationContext())
                .load(imageCover)
                .placeholder(R.drawable.progress_image_animation)
                .into(imageView);

        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();

        pd.dismiss();
    }

    public void onShareClick(String url){
        try {
            List<Intent> targetShareIntents = new ArrayList<>();
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType(UtilityConstants.TEXT_TYPE);
            List<ResolveInfo> resInfos = getApplicationContext().getPackageManager().queryIntentActivities(shareIntent, 0);
            if (!resInfos.isEmpty()) {
                for (ResolveInfo resInfo : resInfos) {
                    String packageName = resInfo.activityInfo.packageName;
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType(UtilityConstants.TEXT_TYPE);
                    intent.putExtra(Intent.EXTRA_TEXT, url);
                    intent.setPackage(packageName);
                    targetShareIntents.add(intent);
                }
                if (!targetShareIntents.isEmpty()) {
                    Intent chooserIntent = Intent.createChooser(targetShareIntents.remove(0), UtilityConstants.SHARE_APPS);
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
                    startActivity(chooserIntent);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
