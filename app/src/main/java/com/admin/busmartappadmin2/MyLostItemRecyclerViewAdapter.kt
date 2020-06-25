package com.admin.busmartappadmin2


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_lost_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [LostItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyLostItemRecyclerViewAdapter(
    private val mValues: List<LostItem>,
    private val mListener: LostItemFragment.OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyLostItemRecyclerViewAdapter.ViewHolder>() {
    var db = FirebaseFirestore.getInstance()
    private val mOnClickListener: View.OnClickListener
    lateinit var context : Context

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as LostItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_lost_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mContentView.text = item.description

        // Load image
        Glide.with(holder.mContentView.context)
            .load(item.downloadUri)
            .into(holder.lostItemImage)
        holder.mContentView.text = item.description
        holder.mButtonremovePost.setOnClickListener{
            db.collection("lostitems").document(item.documentId.toString())
                .delete()
                .addOnSuccessListener {
                    mValues.drop(holder.adapterPosition)
                    notifyItemRemoved(holder.adapterPosition)
                    notifyItemRangeChanged(position,itemCount)
                    notifyDataSetChanged()
                    val builder = AlertDialog.Builder(context)
                    //set title for alert dialog
                    builder.setTitle("Alert")
                    //set message for alert dialog
                    builder.setMessage("This Post will be deleted in few minutes. please click refresh for the changes to take affect.")
                    builder.setIcon(android.R.drawable.ic_dialog_alert)
                    builder.setPositiveButton("OK"){dialogInterface, which ->
                    }
                    val alertDialog: AlertDialog = builder.create()
                    // Set other dialog properties
                    alertDialog.setCancelable(false)
                    alertDialog.show()

                }
                .addOnFailureListener {  }
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val lostItemImage: ImageView = mView.findViewById(R.id.lostItemImage)
        val mButtonremovePost: Button=mView.btnRemovePost
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
