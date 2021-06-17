package com.internshala.example.foodapp


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import com.internshala.example.foodapp.util.ConnectionManager
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.json.JSONObject


class DashboardFragment : Fragment() {


    lateinit var recyclerDashboard:RecyclerView
    lateinit var layoutManager:RecyclerView.LayoutManager
    lateinit var recyclerAdapter:DashboardRecyclerAdapter
    var resInfoList= arrayListOf<Restraunts>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerDashboard=view.findViewById(R.id.recyclerDashboard)
        layoutManager=LinearLayoutManager(activity)


        val queue=Volley.newRequestQueue(activity as Context)
        val url="http://13.235.250.119/v2/restaurants/fetch_result/"

        if(ConnectionManager().checkConnectivity(activity as Context)) {
            val jsonObjectRequest =
                object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {

                    val jsonArray=it.getJSONObject("data")

                    try {
                        val success=jsonArray.getBoolean("success")


                        if(success) {
                            //val data = it.getJSONArray("data")
                            val jsonObject1=jsonArray.getJSONArray("data")
                            for (i in 0 until jsonObject1.length()) {
                                val restrauntsJsonObject = jsonObject1.getJSONObject(i)
                                val resObject = Restraunts(
                                    restrauntsJsonObject.getString("id"),
                                    restrauntsJsonObject.getString("name"),
                                    restrauntsJsonObject.getString("rating"),
                                    restrauntsJsonObject.getString("cost_for_one"),
                                    restrauntsJsonObject.getString("image_url")

                                )
                                resInfoList.add(resObject)
                                recyclerAdapter= DashboardRecyclerAdapter(activity as Context,resInfoList)
                                recyclerDashboard.adapter=recyclerAdapter
                                recyclerDashboard.layoutManager=layoutManager

                                /*recyclerDashboard.addItemDecoration((
                                        DividerItemDecoration(

                                            recyclerDashboard.context,
                                            (layoutManager as LinearLayoutManager).orientation
                                        )
                                        ))*/
                            }

                        }
                        else
                            Toast.makeText(activity as Context,"error occudred", Toast.LENGTH_LONG).show()


                    }
                    catch(e:Exception){

                        Toast.makeText(activity as Context,"error occudred 111", Toast.LENGTH_LONG).show()
                    }



                }, Response.ErrorListener { }) {
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
                val dialog= AlertDialog.Builder(activity as Context)
                dialog.setTitle("Failure")
                dialog.setMessage("connection not found")
                dialog.setPositiveButton("Ok"){text,listener ->
                }
                dialog.setNegativeButton("Cancel"){text,listener -> }
                dialog.create()
                dialog.show()
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        activity?.finish()
    }




}
