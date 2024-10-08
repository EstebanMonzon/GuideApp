package com.ort.guideapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.play.integrity.internal.ac
import com.ort.guideapp.R
import com.ort.guideapp.entities.Activity

class ActivityAdapter(
    var activityList : MutableList<Activity>,
    var onClick : (Int) -> Unit
    ) : RecyclerView.Adapter<ActivityAdapter.ActivityHolder>() {

    class ActivityHolder (v : View) : RecyclerView.ViewHolder(v){
        private var v = v
        init {
            this.v = v
        }
        fun setTitle(title : String){
            val txtTitle : TextView = v.findViewById(R.id.txtTitle)
            txtTitle.text = title
        }
        fun setCity(desc : String){
            val txtCity : TextView = v.findViewById(R.id.txtCity)
            txtCity.text = desc
        }
        fun setRate(rate : Int){
            val txtRate : TextView = v.findViewById(R.id.txtRate)
            txtRate.text = rate.toString()
        }
        fun getCard() : CardView {
            return v.findViewById(R.id.activityCard)
        }
        fun getBtn() : Button {
            return v.findViewById(R.id.btnActivity)
        }
        fun getImage(): ImageView {
            return v.findViewById(R.id.image_activity)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_activity, parent, false)
        return ActivityHolder(view)
    }

    override fun getItemCount(): Int {
        return activityList.size
    }

    override fun onBindViewHolder(holder: ActivityHolder, position: Int) {
        holder.setTitle(activityList[position].title)
        holder.setCity(activityList[position].city)
        holder.setRate(activityList[position].rate)
        holder.getBtn().setOnClickListener{
            onClick(position)
        }
        Glide.with(holder.getImage())
            .load(activityList[position].activityPhoto)
            .centerCrop()
            .into(holder.getImage())
    }
}