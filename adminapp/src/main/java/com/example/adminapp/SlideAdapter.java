package com.example.adminapp;

// src/com/example/myapp/SlideAdapter.java

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SlideAdapter extends PagerAdapter {

    private Context context;
    private int[] slideImages = {R.drawable.promotion1, R.drawable.promotion1, R.drawable.promotion1}; // Replace with your image resources

    public SlideAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.slider_layout, container, false);

        ImageView slideImageView = slideLayout.findViewById(R.id.slideImageView);
        slideImageView.setImageResource(slideImages[position]);

        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public int getCount() {
        return slideImages.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
