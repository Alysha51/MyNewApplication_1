package com.example.adminapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.viewpager.widget.ViewPager
import com.example.adminapp.ModelPreferencesManager.put
import com.example.adminapp.interfaces.CategorySelectionListener
import com.google.firebase.messaging.FirebaseMessaging

class CategorySelectorActivity : AppCompatActivity(), CategorySelectionListener {
    private var categories: ArrayList<Category>? = null
    private val allItems: ArrayList<Item>? = null
    private var selectedItems: ArrayList<Item>? = null
    private var gridView: GridView? = null
    private val listView: ListView? = null
    private var categoryAdapter: CategoryAdapter? = null
    private var listViewOption: TextView? = null
    private var pageViewOption: ImageView? = null
    private val bannerImage: ImageView? = null
    private var dropdownButton: ImageButton? = null
    private var dropdownMenu: LinearLayout? = null
    private var option24Hours: TextView? = null
    private var optionDaily: TextView? = null
    private var optionWeekly: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_selector)
        registerfcm()
        listViewOption = findViewById(R.id.listViewOption)
        pageViewOption = findViewById(R.id.pageViewOption)
        dropdownButton = findViewById(R.id.dropdownButton)
        dropdownMenu = findViewById(R.id.dropdownMenu)
        option24Hours = findViewById(R.id.option24Hours)
        optionDaily = findViewById(R.id.optionDaily)
        optionWeekly = findViewById(R.id.optionWeekly)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val slideAdapter = SlideAdapter(this)
        viewPager.adapter = slideAdapter
        pageViewOption!!.setOnClickListener(View.OnClickListener {
            if (dropdownMenu!!.getVisibility() == View.VISIBLE) {
                dropdownMenu!!.setVisibility(View.GONE)
            } else {
                dropdownMenu!!.setVisibility(View.VISIBLE)
            }
        })
        option24Hours!!.setOnClickListener(View.OnClickListener { // Handle 24 Hours option selection
            dropdownMenu!!.setVisibility(View.GONE)
        })
        optionDaily!!.setOnClickListener(View.OnClickListener { // Handle Daily option selection
            dropdownMenu!!.setVisibility(View.GONE)
        })
        optionWeekly!!.setOnClickListener(View.OnClickListener { // Handle Weekly option selection
            dropdownMenu!!.setVisibility(View.GONE)
        })
        showPageView()
        dropdownButton!!.setOnClickListener(View.OnClickListener { v -> showPopupMenu(v) })
        categories = ArrayList()
        categories!!.add(Category(R.drawable.ic_category, "Category 1"))
        categories!!.add(Category(R.drawable.ic_category, "Category 2"))
        categories!!.add(Category(R.drawable.ic_category, "Category 3"))
        categories!!.add(Category(R.drawable.ic_category, "Category 4"))
        categories!!.add(Category(R.drawable.ic_category, "Category 5"))
        categories!!.add(Category(R.drawable.ic_category, "Category 6"))
        gridView = findViewById(R.id.categoryGridView)
        categoryAdapter = CategoryAdapter(this, this, categories)
        gridView!!.setAdapter(categoryAdapter)
        val PromoCategories = ModelPreferencesManager.get<PromoCategories>("promoCategories")
        if (PromoCategories?.categories?.isNotEmpty()?:false){
            for (category in PromoCategories?.categories!!) {
                categories!!.find{
                    it.name==category.name
                }?.isSelected=true

            }

        }

        // Start ListViewActivity when "List View" option is clicked
        val selectAllButton = findViewById<Button>(R.id.selectAllButton)
        selectAllButton.setOnClickListener {
            for (category in categories!!) {
                category.isSelected = true
            }
            categoryAdapter!!.notifyDataSetChanged()
        }
        val selectButton = findViewById<Button>(R.id.listViewOption)
        selectButton.setOnClickListener {
            val selectedCategories = ArrayList<Category>()
            for (category in categories!!) {
                if (category.isSelected) {
                    selectedCategories.add(category)
                }
            }
            val promoCategories = PromoCategories(selectedCategories)
            put(promoCategories, "promoCategories")


            // Create a new intent to open SelectedItemsActivity
            val intent = Intent(this@CategorySelectorActivity, ListViewActivity::class.java)
            //                    intent.putParcelableArrayListExtra("selectedItems", selectedItems);
            startActivity(intent)
        }
    }

    private fun showPopupMenu(anchorView: View) {
        val popupMenu = PopupMenu(this, anchorView)
        popupMenu.menuInflater.inflate(R.menu.notification_options, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { true }
        popupMenu.show()
    }

    private fun showNotification(s: String) {}
    private fun showPageView() {
        // Show page view related content
        // For example, if you have a ViewPager or fragment for page view, you can add them here.
//        bannerImage.setVisibility(View.VISIBLE);
        // Add additional logic for page view content
    }

    private fun registerfcm() {
        FirebaseMessaging.getInstance().subscribeToTopic("promoNotification")
            .addOnCompleteListener { task ->
                var msg = "Subscribed"
                if (!task.isSuccessful) {
                    msg = "Subscribe failed"
                }
                Log.d("FCM", msg)
                Toast.makeText(this@CategorySelectorActivity, msg, Toast.LENGTH_SHORT).show()
            }
    }

    private fun displaySelectedItems(selectedCategories: ArrayList<Category>) {
        selectedItems = ArrayList()

        // Assuming you have a list of allItems with associated categories
        for (item in allItems!!) {
            for (category in selectedCategories) {
                if (item.category == category.name) {
                    selectedItems!!.add(item)
                    break // No need to check other categories for this item
                }
            }
        }

        // Create an adapter for the ListView
        val itemAdapter = ItemAdapter(this, selectedItems)

        // Assign the adapter to the ListView
        listView!!.adapter = itemAdapter as ListAdapter
    }

    override fun onCategorySelected(category: Category) {
        for (category1 in categories!!) {
            if (category.name == category1.name) {
//            category.setSelected();
                if (category.isSelected) category1.isSelected = true else category1.isSelected =
                    false
            }
        }
        categoryAdapter!!.notifyDataSetChanged()
    }
}