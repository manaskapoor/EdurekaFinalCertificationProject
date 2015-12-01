package com.edureka.movies.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.edureka.movies.R;
import com.edureka.movies.trendingmovies.MovieHomePageDisplay;
import com.edureka.movies.pojoclasses.TrendingMovies;
import com.edureka.movies.utils.UtilityConstants;
import com.edureka.movies.utils.UtilityFunctions;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class TrendingMoviesAdapter extends ArrayAdapter<TrendingMovies> implements View.OnClickListener , Filterable{
    private Activity activity;
    private Context c;
    private static LayoutInflater inflater=null;
    private List<TrendingMovies> allModelItemsArray;
    private List<TrendingMovies> filteredModelItemsArray;
    TrendingMovies tempValues;
    Filter mFilter;
    public TrendingMoviesAdapter(Activity av,Context a,List<TrendingMovies> d) {
        super(a, R.layout.movie_list_view, d);
        this.c = a;
        activity = av;
        this.allModelItemsArray = new ArrayList<>();
        allModelItemsArray.addAll(d);
        this.filteredModelItemsArray = new ArrayList<>();
        filteredModelItemsArray.addAll(allModelItemsArray);
        inflater = (LayoutInflater)c.
               getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try {
            getFilter();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static class ViewHolder{
        public TextView movieTitle;
        public TextView movieTagline;
        public TextView movieRating;
        public ImageView thumImage;
    }
    @Override
    public int getCount() {
        return filteredModelItemsArray.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){
            vi = inflater.inflate(R.layout.movie_list_view, null);
            holder = new ViewHolder();
            holder.movieTitle = (TextView) vi.findViewById(R.id.movieTitle);
            holder.movieTagline=(TextView)vi.findViewById(R.id.movieTagline);
            holder.movieRating=(TextView)vi.findViewById(R.id.movieRating);
            holder.thumImage=(ImageView)vi.findViewById(R.id.image);

            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(filteredModelItemsArray.size()<=0){
            Log.d("No data","No data");
        }
        else
        {
            tempValues=null;
            tempValues = (TrendingMovies) filteredModelItemsArray.get(position);
            if(tempValues.getTitle()!=null){
                holder.movieTitle.setText( tempValues.getTitle() );
            }
            if(tempValues.getTagline()!=null && !(tempValues.getTagline().isEmpty())) {
                    holder.movieTagline.setVisibility(View.VISIBLE);
                    holder.movieTagline.setText(" -> " +tempValues.getTagline());
            }else{
                holder.movieTagline.setVisibility(View.GONE);
            }
            holder.movieRating.setText(" -> Imdb Rating : " + UtilityFunctions.round(tempValues.getRating(),2)+"/10");
            Picasso.with(c)
                    .load(tempValues.getThumbImage())
                    .placeholder(R.drawable.small_loader_animation)
                    .error(R.drawable.noimageavailable)
                    .into(holder.thumImage);
            holder.movieTitle.setOnClickListener(new OnItemClickListener(position));
            holder.movieTagline.setOnClickListener(new OnItemClickListener(position));
            holder.thumImage.setOnClickListener(new OnItemClickListener(position));
            holder.movieRating.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }


    @Override
    public void onClick(View v) {

    }
    private class OnItemClickListener  implements View.OnClickListener{
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            TrendingMovies tempValues = ( TrendingMovies) filteredModelItemsArray.get(mPosition);
            Intent intent = new Intent(c, MovieHomePageDisplay.class);
            intent.putExtra(UtilityConstants.MOVIE, tempValues);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
        }
    }


    public void titleSort(){
        Collections.sort(filteredModelItemsArray, new Comparator<TrendingMovies>() {
            @Override
            public int compare(TrendingMovies lhs, TrendingMovies rhs) {
                String name1 = lhs.getTitle();
                String name2 = rhs.getTitle();
                return name1.compareTo(name2);
            }
        });
        notifyDataSetChanged();
    }

    public void ratingSort(){
        Collections.sort(filteredModelItemsArray, new Comparator<TrendingMovies>() {
            @Override
            public int compare(TrendingMovies lhs, TrendingMovies rhs) {

                float name1 = lhs.getRating();
                float name2 = rhs.getRating();
                return Float.compare(name2, name1);
            }
        });
        notifyDataSetChanged();
    }

    public void ReleaseDateSort(){
        Collections.sort(filteredModelItemsArray, new Comparator<TrendingMovies>() {
            @Override
            public int compare(TrendingMovies lhs, TrendingMovies rhs) {

                String name1 = lhs.getReleased();
                String name2 = rhs.getReleased();
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(UtilityConstants.DATE_FORMAT);
                    if (name1 == "null" || name2 == "null"){
                        return 0;
                    }
                    Date d1 = sdf.parse(name1);
                    Date d2 = sdf.parse(name2);
                    return d2.compareTo(d1);
                }catch (Exception e){
                    e.printStackTrace();
                    return 0;
                }
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {

        super.notifyDataSetChanged();
    }

    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ModelFilter();
        }
        return mFilter;
    }
    private class ModelFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if(constraint != null && constraint.toString().length() > 0)
            {
                ArrayList<TrendingMovies> filteredItems = new ArrayList<>();

                for(int i = 0, l = allModelItemsArray.size(); i < l; i++){
                    TrendingMovies m = allModelItemsArray.get(i);
                    if(m.getTitle().toLowerCase().contains(constraint)) {
                        filteredItems.add(m);
                    }
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            }
            else
            {
                synchronized(this)
                {
                    result.values = allModelItemsArray;
                    result.count = allModelItemsArray.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            filteredModelItemsArray = (ArrayList<TrendingMovies>)results.values;
            if(results.count>0) {
                notifyDataSetChanged();
            }
            else {
                notifyDataSetInvalidated();
            }
        }
    }

    @Override
    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
    }
}
