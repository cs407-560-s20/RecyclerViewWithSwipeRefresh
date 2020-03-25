package com.example.recyclerviewwithswiperefresh

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_item.view.*


class MyRecyclerAdapter(private val contacts: ArrayList<Contact>) : RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>() {

    var count = 0
    private val TAG = "MyRecyclerAdapter"
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflate a layout from our XML (row_item.XML) and return the holder

        Log.d(TAG, "onCreateViewHolder: ${count++}")

        // create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val currentItem = contacts[position]
        holder.name.text = currentItem.name
        holder.age.text = currentItem.age.toString()

        //holder.profileImage.setImageResource(currentItem.profileImage)

        // The URL must be https since Android does not allow your app to connect http (not secure)
        // If you want to connect a http address, add android:usesCleartextTraffic="true"
        // to your Manifest file somewhere in  <application ...   >
        Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(holder.profileImage)
        Picasso.get().load(currentItem.profileImage).into(holder.profileImage)



        Log.d(TAG, "onBindViewHolder: $position")
    }

    override fun getItemCount(): Int {
        // Return the size of your dataset (invoked by the layout manager)
        return contacts.size
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    inner class MyViewHolder (itemView: View): RecyclerView.ViewHolder (itemView){
        // This class will represent a single row in our recyclerView list
        // This class also allows caching views and reuse them
        // Each MyViewHolder object keeps a reference to 3 view items in our row_item.xml file
        val name = itemView.tv_name
        val age = itemView.tv_age
        val profileImage = itemView.image_profile


        // Set onClickListener to show a toast message for the selected row item in the list
        init {

            itemView.setOnClickListener{
                val selectedItem = adapterPosition
                Toast.makeText(itemView.context, "You clicked on $selectedItem",
                    Toast.LENGTH_SHORT).show()
            }

            // Set onLongClickListener to show a toast message and remove the selected row item from the list
            itemView.setOnLongClickListener {

                val selectedItem = adapterPosition
                contacts.removeAt(selectedItem)
                notifyItemRemoved(selectedItem)
                Toast.makeText(itemView.context, "Long press, deleting $selectedItem",
                    Toast.LENGTH_SHORT).show()

                return@setOnLongClickListener true
            }

        }

    }
}
