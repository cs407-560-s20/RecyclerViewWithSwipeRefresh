package com.example.recyclerviewwithswiperefresh

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    var refreshCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // specify an viewAdapter for the dataset
        recycler_view.adapter = MyRecyclerAdapter(generateContact(20))

        // use a linear layout manager
        recycler_view.layoutManager = LinearLayoutManager(this)

        // if you want, you can make the layout of the recyclerview horizontal as follows
        //recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        // Add a divider between rows -- Optional
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recycler_view.addItemDecoration(dividerItemDecoration)

        // use this setting to improve performance if you know the size of the RecyclerView is fixed
        //recycler_view.setHasFixedSize(true)


        // Setup refresh listener which triggers new data loading
        swipe_refresh_layout.setOnRefreshListener {

            // Let's assume that we want to add 2 items each the user swipes up to refresh
            // More realistically, the data comes from a server or a database
            refreshCount +=2
            Log.d(TAG, "Added $refreshCount items...")

            // Update the adapter with new data. Alternatively, notifyDataSetChanged() can be called
            recycler_view.adapter = MyRecyclerAdapter(generateContact(20 + refreshCount))

            // Make sure to set isRefreshing false to hide the refreshing animation
            swipe_refresh_layout.isRefreshing = false
        }

        // set a different color for the refreshing animation -- Optional
        swipe_refresh_layout.setColorSchemeColors(Color.BLUE)

    }

    private fun generateContact(size: Int) : ArrayList<Contact>{

        // A helper function to create specified amount of dummy contact data
        val contacts = ArrayList<Contact>()

        for (i in 0..size){
            val person = Contact("John Doe-$i", i, "https://picsum.photos/150?random=$i")
            contacts.add(person)
        }

        // Reverse the array so that the newly added data can be seen at the top
        contacts.reverse()
        return contacts
    }
}
