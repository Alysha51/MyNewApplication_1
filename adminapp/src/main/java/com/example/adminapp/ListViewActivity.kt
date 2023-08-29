package com.example.adminapp

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListViewActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var itemAdapter: ItemAdapter? = null
    private var itemList: MutableList<Item?>? = null
    private val selectedItemsListView: ListView? = null
    private val selectedItems: ArrayList<Item>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_view_page)
        //        
       val PromoCategories = ModelPreferencesManager.get<PromoCategories>("promoCategories")
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView!!.setLayoutManager(LinearLayoutManager(this))
        itemList = ArrayList()
        itemAdapter = ItemAdapter(this, itemList)
        recyclerView!!.setAdapter(itemAdapter)
        val itemsRef = FirebaseDatabase.getInstance().getReference("items")
        itemsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                itemList!!.clear()
                for (itemSnapshot in dataSnapshot.children) {
                    val item = itemSnapshot.getValue(
                        Item::class.java
                    )

                    if (PromoCategories?.categories?.isNotEmpty() ?: false) {
                        if (PromoCategories?.categories?.any { it.name .lowercase()== item?.category?.lowercase() }?:false) {
                            itemList!!.add(item)
                        }
                    } else
                    {
                        itemList!!.add(item)
                    }
                }
                itemAdapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
            }
        })
    }
}