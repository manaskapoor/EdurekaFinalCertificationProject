package com.edureka.movies.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edureka.movies.R;

public class TutorialsAdapter extends PagerAdapter {

    Context context;
    String[] rank;
    String[] middleText;
    int[] flag;

    LayoutInflater inflater;

    public TutorialsAdapter(Context context, String[] rank, int[] flag, String[] middleText) {
        this.context = context;
        this.flag = flag;
        this.rank = rank;
        this.middleText = middleText;

    }

    @Override
    public int getCount() {
        return rank.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        ImageView imgflag;
        TextView middleTextView;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.tutorials_view, container,
                false);

        middleTextView= (TextView) itemView.findViewById(R.id.detailText);
        imgflag = (ImageView) itemView.findViewById(R.id.tutorialImage);
        middleTextView.setText(middleText[position]);
        imgflag.setImageResource(flag[position]);
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }
}

