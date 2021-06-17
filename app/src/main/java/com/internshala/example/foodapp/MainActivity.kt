package com.internshala.example.foodapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.internshala.example.foodapp.util.ConnectionManager
import kotlinx.android.synthetic.main.activity_login.view.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var mobileNumber:EditText
    lateinit var password:EditText
    lateinit var login:Button
    lateinit var forgotPassword:TextView

    var phoneNumber:String?="100"
    var passwordSend:String?="100"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide();
        setContentView(R.layout.activity_login)

        mobileNumber=findViewById(R.id.mobileNumber)
        password=findViewById(R.id.password)
        login=findViewById(R.id.btnLogin)
        forgotPassword=findViewById(R.id.txtForgotPassword)


        //phoneNumber=mobileNumber.text.toString()
        //passwordSend=password.text.toString()
        login.setOnClickListener{

            if(ConnectionManager().checkConnectivity(this@MainActivity))
            {
                phoneNumber=mobileNumber.text.toString()
                passwordSend= password.text.toString()
                val url="http://13.235.250.119/v2/login/fetch_result/"
                val queue= Volley.newRequestQueue(this@MainActivity)
                val jsonParams=JSONObject()
                jsonParams.put("mobile_number",phoneNumber)
                jsonParams.put("password",passwordSend)
               // val intent=Intent(this@MainActivity,LoginActivity::class.java)
                //startActivity(intent)

                val jsonObject=object: JsonObjectRequest(Request.Method.POST,url,jsonParams,
                    Response.Listener{
                    try {
                        val jsonArray=it.getJSONObject("data")
                        val success=jsonArray.getBoolean("success")
                        val jsonObject1=jsonArray.getJSONObject("data")
                        Toast.makeText(this@MainActivity,"clicked $success",Toast.LENGTH_LONG).show()
                        if(success)
                        {
                            Toast.makeText(this@MainActivity,"clicked",Toast.LENGTH_LONG).show()
                            //val jsonArray=it.getJSONObject("data")

                            //val p=Profile(jsonObject1.getString("user_id"),jsonObject1.getString("name"),jsonObject1.getString("mobile_number"),jsonObject1.getString("email"),jsonObject1.getString("address"))
                            //profile.kt has all the variables...so storing value of the user to it and whenever it is required it can be used by calling the function of that class which returns the details
                            //values are stored in p

                            val intent=Intent(this@MainActivity,LoginActivity::class.java)
                            intent.putExtra("user_id",jsonObject1.getString("user_id"))
                            intent.putExtra("name",jsonObject1.getString("name"))
                            intent.putExtra("mobile_number",jsonObject1.getString("mobile_number"))
                            intent.putExtra("email",jsonObject1.getString("email"))
                            intent.putExtra("address",jsonObject1.getString("address"))
                            startActivity(intent)

                            /////////////////////////

                        }
                        else
                        {
                            Toast.makeText(this@MainActivity,"error111",Toast.LENGTH_LONG).show()
                        }

                    }
                    catch (e:Exception)
                    {
                        Toast.makeText(this@MainActivity,"error222 $e",Toast.LENGTH_LONG).show()
                    }
                },Response.ErrorListener{
                        Toast.makeText(this@MainActivity,"error333",Toast.LENGTH_LONG).show()
                    }){
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "9dcb3a938d216a"
                        return headers
                    }
                }
                queue.add(jsonObject)

            }
            else{
                //net not available
                val dialog= AlertDialog.Builder(this@MainActivity)
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
    override fun onPause() {
        super.onPause()
        finish()
    }
}
