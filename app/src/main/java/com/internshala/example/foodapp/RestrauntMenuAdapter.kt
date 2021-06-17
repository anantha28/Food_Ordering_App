package com.internshala.example.foodapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RestrauntMenuAdapter(val context: Context,val itemList:ArrayList<RestrauntMenu>):RecyclerView.Adapter<RestrauntMenuAdapter.RestrauntMenuViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestrauntMenuViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_restrauntmenu_singlerow,parent,false)
        return RestrauntMenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RestrauntMenuViewHolder, position: Int) {
        val text=itemList[position]
        holder.id.text=text.food_id
        holder.name.text=text.food_name
        holder.costForOne.text=text.costOfFood
    }

    class RestrauntMenuViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val id:TextView=view.findViewById(R.id.foodNumber)
        val name:TextView=view.findViewById(R.id.txtMenuRecyclerRowItem)
        val costForOne:TextView=view.findViewById(R.id.costOfFood)
        val add:Button=view.findViewById(R.id.btnAdd)
    }

}