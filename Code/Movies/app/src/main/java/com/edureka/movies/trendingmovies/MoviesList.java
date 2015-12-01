package com.edureka.movies.trendingmovies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.edureka.movies.adapters.TrendingMoviesAdapter;
import com.edureka.movies.R;
import com.edureka.movies.application.ApplicationController;
import com.edureka.movies.pojoclasses.TrendingMovies;
import com.edureka.movies.utils.UtilityConstants;
import com.edureka.movies.utils.UtilityFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoviesList extends ActionBarActivity {
    ListView trending;
    Spinner sort;
    EditText search;
    TrendingMoviesAdapter adapter;
    ProgressDialog pd;
    ImageButton retry;
    public List<TrendingMovies> CustomListViewValuesArr= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);
        getSupportActionBar().hide();

        trending= (ListView)findViewById(R.id.searchResultList);
        sort= (Spinner)findViewById(R.id.sort);
        search= (EditText)findViewById(R.id.search);
        retry= (ImageButton)findViewById(R.id.retry);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setFocusable(true);

            }
        });
        ArrayAdapter<SortingCriteria> searchAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_text, SortingCriteria.values());

        sort.setAdapter(searchAdapter);
        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String criteriaSelected=((SortingCriteria) sort.getSelectedItem()).name();
                if (criteriaSelected.equalsIgnoreCase(UtilityConstants.TITLE))  {
                    adapter.titleSort();
                } else if (criteriaSelected.equalsIgnoreCase(UtilityConstants.RATING)) {
                    adapter.ratingSort();
                } else if (criteriaSelected.equalsIgnoreCase("release_date")) {
                    adapter.ReleaseDateSort();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                trending.setEmptyView(findViewById(android.R.id.empty));
                try {
                    if (adapter != null) {
                        adapter.getFilter().filter(s);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        if(UtilityFunctions.isInternetOn(MoviesList.this)) {
            generateTrendingMoviewData();
        }else{
            trending.setVisibility(View.GONE);
            retry.setVisibility(View.VISIBLE);
        }

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(UtilityFunctions.isInternetOn(MoviesList.this)) {
                    trending.setVisibility(View.VISIBLE);
                    retry.setVisibility(View.GONE);
                    generateTrendingMoviewData();
                }else{
                    Toast.makeText(getApplicationContext(),UtilityConstants.NETWORK_ERROR,Toast.LENGTH_SHORT).show();
                    trending.setVisibility(View.GONE);
                    retry.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void generateTrendingMoviewData(){
        pd = new ProgressDialog(this);
        pd.setMessage(UtilityConstants.LOADER_MESSAGE);
        pd.show();

        RequestQueue queue = ApplicationController.getInstance().getRequestQueue();
        String url = UtilityConstants.API_URL;
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response) {
                        CustomListViewValuesArr.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                setMoviesData(jsonObject);
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }

                        adapter=new TrendingMoviesAdapter(MoviesList.this,MoviesList.this,CustomListViewValuesArr);
                        trending.setAdapter(adapter);
                        if(pd!=null){
                            pd.dismiss();
                        }
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                if(pd!=null){
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(),UtilityConstants.GENERAL_ERROR,Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type", UtilityConstants.CONTENT_TYPE);
                params.put("trakt-api-version", UtilityConstants.API_VERSION);
                params.put("trakt-api-key", UtilityConstants.API_KEY);
                return params;

            }
        };

        queue.add(jsonObjReq);

    }
    private void setMoviesData(JSONObject trendingMovies){
        try{
            TrendingMovies tm =  new TrendingMovies();
                JSONObject movieDetailObject= trendingMovies.getJSONObject(UtilityConstants.MOVIE);
                tm.setTitle(movieDetailObject.getString(UtilityConstants.TITLE));
                tm.setYear(Integer.parseInt(movieDetailObject.getString(UtilityConstants.YEAR)));
                tm.setReleased(movieDetailObject.getString(UtilityConstants.RELEASED));
                tm.setRuntime(Integer.parseInt(movieDetailObject.getString(UtilityConstants.RUNTIME)));
                tm.setTrailer(movieDetailObject.getString(UtilityConstants.TRAILER));
                tm.setHomepage(movieDetailObject.getString(UtilityConstants.HOMEPAGE));
                tm.setTagline(movieDetailObject.getString(UtilityConstants.TAGLINE));
                tm.setOverview(movieDetailObject.getString(UtilityConstants.OVERVIEW));
                tm.setCertificate(movieDetailObject.getString(UtilityConstants.CERTIFICATION));
                tm.setGenre(movieDetailObject.getString(UtilityConstants.GENRES));
                tm.setRating(Float.parseFloat(movieDetailObject.getString(UtilityConstants.RATING)));
                JSONObject ImageDetailObject = movieDetailObject.getJSONObject(UtilityConstants.IMAGES);
                JSONObject PosterDetailObject = ImageDetailObject.getJSONObject(UtilityConstants.POSTER);
                tm.setThumbImage(PosterDetailObject.getString(UtilityConstants.THUMBNAIL));
                JSONObject BannerDetailObject = ImageDetailObject.getJSONObject(UtilityConstants.FANART);
                tm.setBannerImage(BannerDetailObject.getString(UtilityConstants.FULL_IMAGE));
                tm.setFullPoster(PosterDetailObject.getString(UtilityConstants.FULL_IMAGE));
                CustomListViewValuesArr.add(tm);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent("android.intent.action.MAIN");
        i.addCategory("android.intent.category.HOME");
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }
    public enum SortingCriteria{
        select("---Sort by---"),
        title("Title"),
        rating("Rating"),
        release_date("Released Date");

        private String theState;

        SortingCriteria(String aState) {
            theState = aState;
        }

        @Override public String toString() {
            return theState;
        }
    }
}
