package com.example.mynewapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PageActivity extends AppCompatActivity {

    private TextView pageViewOption;
    private TextView listViewOption;
    private ImageView bannerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_view);

        // Initialize views
        pageViewOption = findViewById(R.id.pageViewOption);
        listViewOption = findViewById(R.id.listViewOption);
        bannerImage = findViewById(R.id.bannerImage);

        // Set initial visibility of banner and handle navigation clicks
        showPageView();
        pageViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPageView();
            }
        });

        listViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add a log message to verify the click event
                Log.d("MainActivity", "List View option clicked");

                // Start ListViewActivity when "List View" option is clicked
                Intent intent = new Intent(PageActivity.this, ListViewActivity.class);
                startActivity(intent);
            }
        });

    }

    private void showPageView() {
        // Show page view related content
        // For example, if you have a ViewPager or fragment for page view, you can add them here.
        bannerImage.setVisibility(View.VISIBLE);
        // Add additional logic for page view content
    }

}
