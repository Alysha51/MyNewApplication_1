package com.example.adminapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class PageActivity extends AppCompatActivity {

    private ArrayList<Category> categories;
    private GridView gridView;

    private TextView listViewOption;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Item> allItems;
    private ArrayList<Item> selectedItems;
    private ListView listView;

    private ImageView pageViewOption;
    private ImageView bannerImage;
    private ImageButton dropdownButton;
    private LinearLayout dropdownMenu;
    private TextView option24Hours;
    private TextView optionDaily;
    private TextView optionWeekly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_view);
        registerfcm();

        // Initialize views
        pageViewOption = findViewById(R.id.pageViewOption);
        listViewOption = findViewById(R.id.listViewOption);
//        bannerImage = findViewById(R.id.bannerImage);
        dropdownButton = findViewById(R.id.dropdownButton);
        dropdownMenu = findViewById(R.id.dropdownMenu);
        option24Hours = findViewById(R.id.option24Hours);
        optionDaily = findViewById(R.id.optionDaily);
        optionWeekly = findViewById(R.id.optionWeekly);

        ViewPager viewPager = findViewById(R.id.viewPager);
        SlideAdapter slideAdapter = new SlideAdapter(this);
        viewPager.setAdapter(slideAdapter);


        pageViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dropdownMenu.getVisibility() == View.VISIBLE) {
                    dropdownMenu.setVisibility(View.GONE);
                } else {
                    dropdownMenu.setVisibility(View.VISIBLE);
                }
            }
        });

        option24Hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle 24 Hours option selection
                dropdownMenu.setVisibility(View.GONE);
            }
        });

        optionDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Daily option selection
                dropdownMenu.setVisibility(View.GONE);
            }
        });

        optionWeekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Weekly option selection
                dropdownMenu.setVisibility(View.GONE);
            }
        });

        // Set initial visibility of banner and handle navigation clicks
        showPageView();
        dropdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showPopupMenu(v);
            }
        });



        listViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add a log message to verify the click event
                Log.d("PageActivity", "List View option clicked");

                // Start ListViewActivity when "List View" option is clicked
                Intent intent = new Intent(PageActivity.this, ListViewActivity.class);
                startActivity(intent);
            }
        });


    }
    private void showPopupMenu(View anchorView) {
        PopupMenu popupMenu = new PopupMenu(this, anchorView);
        popupMenu.getMenuInflater().inflate(R.menu.notification_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                        return true;
                }

        });
        popupMenu.show();
    }

    private void showNotification(String s) {
    }


    private void showPageView() {
        // Show page view related content
        // For example, if you have a ViewPager or fragment for page view, you can add them here.
        bannerImage.setVisibility(View.VISIBLE);
        // Add additional logic for page view content
    }

    private void registerfcm() {
        FirebaseMessaging.getInstance().subscribeToTopic("promoNotification")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d("FCM", msg);
                        Toast.makeText(PageActivity.this, msg, Toast.LENGTH_SHORT).show();


                    }
                });
    }
}

