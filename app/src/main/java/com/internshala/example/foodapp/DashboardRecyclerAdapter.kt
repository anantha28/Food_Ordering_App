package com.internshala.example.foodapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
//import com.internshala.example.foodapp.R
import kotlinx.android.synthetic.main.recycler_dashboard_single_row.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

//import android.R



class DashboardRecyclerAdapter(val context: Context,val itemList:ArrayList<Restraunts>):RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent,false)
        return DashboardViewHolder(view)
    }

    override fun getItemCount(): Int {
       return itemList.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val restraunt=itemList[position]
        holder.resName.text=restraunt.resName
        holder.resPrice.text=restraunt.costForOne
        holder.resRating.text=restraunt.rating
        Picasso.get().load(restraunt.image).into(holder.resImage)
        //(this as Activity).fragmentManager

        holder.l1Contet.setOnClickListener{
            val intent1= Intent(context,RestrauntMenuListView::class.java)
            intent1.putExtra("id",restraunt.id)
            Toast.makeText(context,"id is ${restraunt.id}",Toast.LENGTH_LONG).show()
            context.startActivity(intent1)
            //val fragment = supportFragmentManager.findFragmentById(R.id.RestrauntMenuAdapter) as RestrauntMenuFragment

            //supportFragmentManager.beginTransaction().replace(R.id.Frame,ProfileFragment()).commit()//calling RestrauntMenuFragment from here


        }

    }

    class DashboardViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val resName:TextView=view.findViewById(R.id.textRecyclerRowItem)
        val resRating:TextView=view.findViewById(R.id.recyclerSingleRowRating)
        val resPrice:TextView=view.findViewById(R.id.recyclerSingleRowPrice)
        val resImage: ImageView =view.findViewById(R.id.recyclerSingleRowImage)
        val l1Contet: RelativeLayout =view.findViewById(R.id.l1Contet)
    }

}