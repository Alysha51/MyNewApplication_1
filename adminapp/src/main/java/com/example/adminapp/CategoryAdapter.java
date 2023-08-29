package com.example.adminapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adminapp.interfaces.CategorySelectionListener;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Category> categories;
    private CategorySelectionListener categoryselect;

    public CategoryAdapter(Context context, CategorySelectionListener categoryselect, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
        this.categoryselect= categoryselect;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        }

        ImageView iconImageView = convertView.findViewById(R.id.iconImageView);
        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        CheckBox categoryCheckBox = convertView.findViewById(R.id.categoryCheckBox);

        final Category category = categories.get(position);
        iconImageView.setImageResource(category.getIconResId());
        nameTextView.setText(category.getName());
        categoryCheckBox.setChecked(category.isSelected());

        categoryCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                category.setSelected(isChecked);
                categoryselect.onCategorySelected(category);
            }
        });

        return convertView;

    }
}

