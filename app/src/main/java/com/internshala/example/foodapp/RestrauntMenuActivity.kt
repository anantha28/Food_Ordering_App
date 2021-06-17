package com.internshala.example.foodapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.internshala.example.foodapp.util.ConnectionManager
import org.json.JSONObject
import java.lang.Exception

class RestrauntMenuActivity :AppCompatActivity()
{
    lateinit var recyclerMenu: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter:RestrauntMenuAdapter
    lateinit var progressLayout: RelativeLayout
    var resInfoList1= arrayListOf<RestrauntMenu>()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.fragment_restraunt_menu)

        Toast.makeText(this@RestrauntMenuActivity,"inside",Toast.LENGTH_LONG).show()

        recyclerMenu=findViewById(R.id.recyclerRestrauntMenu)
        layoutManager= LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        progressLayout = findViewById(R.id.progressLayout)
        progressLayout.visibility =
            View.VISIBLE




        //val intent1= getIntent()
        val id:String=intent.getStringExtra("id")
       //val id:String="5"

        Toast.makeText(this@RestrauntMenuActivity,"${id}",Toast.LENGTH_LONG).show()


        val url="http://13.235.250.119/v2/restaurants/fetch_result/$id"


        val queue= Volley.newRequestQueue(this@RestrauntMenuActivity)
        val jsonParams= JSONObject()
        if(ConnectionManager().checkConnectivity(this@RestrauntMenuActivity)) {
            val jsonObjectRequest =
                object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                    val jsonArray = it.getJSONObject("data")
                    try {
                        val success = jsonArray.getBoolean("success")
                        if (success) {
                            val jsonObject1 = jsonArray.getJSONArray("data")
                            progressLayout.visibility = View.GONE
                            for (i in 0 until jsonObject1.length()) {
                                //val resMenuJsonObject = jsonObject1.getJSONObject(i)
                                val restrauntssJsonObject = jsonObject1.getJSONObject(i)
                                val res1Object = RestrauntMenu(
                                    restrauntssJsonObject.getString("id"),
                                    restrauntssJsonObject.getString("name"),
                                    restrauntssJsonObject.getString("cost_for_one"),
                                    restrauntssJsonObject.getString("restraunt_id")
                                    //restrauntssJsonObject.getString("image_url")
                                )
                                resInfoList1.add(res1Object)
                                recyclerAdapter= RestrauntMenuAdapter(this@RestrauntMenuActivity,resInfoList1)
                                recyclerMenu.adapter=recyclerAdapter
                                recyclerMenu.layoutManager=layoutManager


                                ////////////
                                ////////*//
                                ////////


                            }

                        }
                        else
                            Toast.makeText(this@RestrauntMenuActivity, "error", Toast.LENGTH_LONG)
                    } catch (e: Exception) {
                        Toast.makeText(this@RestrauntMenuActivity, "error", Toast.LENGTH_LONG)
                    }

                }, Response.ErrorListener {

                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "9dcb3a938d216a"
                        return headers
                    }
                }
            queue.add(jsonObjectRequest)
        }
        else{
            //net not available
            val dialog= AlertDialog.Builder(this@RestrauntMenuActivity)
            dialog.setTitle("Failure")
            dialog.setMessage("connection not found")
            dialog.setPositiveButton("Ok"){text,listener ->
            }
            dialog.setNegativeButton("Cancel"){text,listener -> }
            dialog.create()
            dialog.show()
        }
    }
}