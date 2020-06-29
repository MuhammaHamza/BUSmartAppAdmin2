package com.admin.busmartappadmin2

import android.app.AlertDialog
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
import com.admin.busmartappadmin2.service.Injection
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import io.swagger.client.models.Shop
import io.swagger.client.models.ShopType
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

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
            val builder = AlertDialog.Builder(this)
            //View view = getLayoutInflater().inflate(R.layout.progress);
            builder.setView(R.layout.progres)
            builder.setCancelable(false)

            val dialog = builder.create()
            dialog.show()
            val selectedShopType = spShopType.selectedItem
            if (selectedShopType == "Cafe"){
                Injection.provideApiService()
                    .addShop(Shop(UUID.randomUUID().toString(), txtItemName.text.toString(), ShopType.Cafe))
                    .enqueue(object : Callback<Shop> {
                        override fun onFailure(call: Call<Shop>, t: Throwable) {
                            dialog.dismiss()
                            return
                        }

                        override fun onResponse(call: Call<Shop>, response: Response<Shop>) {
                            if (!response.isSuccessful) {
                                dialog.dismiss()
                                return
                            }

                            dialog.dismiss()
                        }
                    })
            }
            else {
                Injection.provideApiService()
                    .addShop(Shop(UUID.randomUUID().toString(), txtItemName.text.toString(), ShopType.Stationary))
                    .enqueue(object : Callback<Shop> {
                        override fun onFailure(call: Call<Shop>, t: Throwable) {
                            dialog.dismiss()
                            return
                        }

                        override fun onResponse(call: Call<Shop>, response: Response<Shop>) {
                            if (!response.isSuccessful) {
                                dialog.dismiss()
                                return
                            }

                            dialog.dismiss()
                        }
                    })

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
