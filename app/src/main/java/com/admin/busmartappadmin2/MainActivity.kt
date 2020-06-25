package com.admin.busmartappadmin2

import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    LostItemFragment.OnListFragmentInteractionListener
{
    val data= ArrayList<ListModel>()
    lateinit var selectedFragment: Fragment
    lateinit var selectFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectedFragment= LostItemFragment()
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            selectedFragment
        ).commit()
        val cafes = arrayListOf(
            "Cafe",
            "Stationary"
        )
        val arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, cafes)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spShopType.adapter = arrayAdapter
        val db = FirebaseFirestore.getInstance()

        btnRefreshItem.setOnClickListener{
            finish();
            startActivity(getIntent());
        }

        btnAddItem.setOnClickListener {
            val selectedShopType = spShopType.selectedItem
            if (selectedShopType == "Cafe"){
                val name = txtItemName.text
                val description = txtItemPrice.text
                if (name != null) {
                    if (description != null) {
                        if (description.length > 5 && name.length > 1) {
                            val newItem: MutableMap<String, Any> = HashMap()
                            newItem["name"] = name.toString()
                            newItem["description"] = name.toString()

                            db.collection("cafes")
                                .document("$name")
                                .collection("$name")
                                .add(newItem)
                                .addOnSuccessListener {
                                    MaterialAlertDialogBuilder(this)
                                        .setTitle("Success")
                                        .setMessage("Added Successfully")
                                        .setPositiveButton("ok", null)
                                        .setIcon(R.mipmap.success)
                                        .show();

                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        applicationContext,
                                        "Failed to add with error: ${e.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }
                    }
                }
            }
            else {
                val name = txtItemName.text
                val description = txtItemPrice.text
                if (name != null) {
                    if (description != null) {
                        if (description.length > 5 && name.length > 1) {
                            val newItem: MutableMap<String, Any> = HashMap()
                            newItem["name"] = name.toString()
                            newItem["description"] = name.toString()

                            db.collection("stationary")
                                .document("$name")
                                .collection("$name")
                                .add(newItem)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        applicationContext,
                                        "Item added successfully",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        applicationContext,
                                        "Failed to add with error: ${e.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }
                    }
                }
            }
        txtItemPrice.setText("")
        txtItemName.setText("")


        }
        //Feching data from firebase
        try {
        }catch (e:Exception)
        {
            Log.d("Error", e.printStackTrace().toString())
        }
    }

    override fun onListFragmentInteraction(item: LostItem?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
//
//    private fun fill_data()
//    {
//        Log.e("Arrived","msg")
//        val stationaryShops = java.util.ArrayList<String>()
//        val cafes = java.util.ArrayList<String>()
//        data.clear()
//        db.collection("stationary")
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    for (document in task.result!!) {
//                        data.add(ListModel("Stationary",document.data.entries.first().value.toString()))
//                    }
//                } else {
//                    Toast.makeText(
//                        applicationContext,
//                        "Error getting documents. ${task.exception}",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }}
//        db.collection("cafes")
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    for (document in task.result!!) {
//                        data.add(ListModel("Cafe",document.data.entries.first().value.toString()))
//                    }
//                } else {
//                    Toast.makeText(
//                        applicationContext,
//                        "Error getting documents. ${task.exception}",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//
//            }}

}
