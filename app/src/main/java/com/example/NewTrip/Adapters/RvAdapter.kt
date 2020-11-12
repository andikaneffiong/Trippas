package com.example.NewTrip.Adapters

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.NewTrip.Database.TripEntity
import com.example.NewTrip.R
import com.example.NewTrip.TripClass

class RvAdapter(private val tripList: List<TripEntity>, private val listener:OnItemClickListener) : RecyclerView.Adapter<RvAdapter.MyViewholder>(){


    inner class  MyViewholder(itemView:View):RecyclerView.ViewHolder(itemView),View.OnClickListener{

        private val rvMenu: ImageView  = itemView.findViewById(R.id.rv_menu)

        val dest: TextView = itemView.findViewById(R.id.destination_rv)
        val destDate: TextView = itemView.findViewById(R.id.destination_date_rv)
        val destTime: TextView = itemView.findViewById(R.id.destination_time_rv)
        val dep: TextView = itemView.findViewById(R.id.depature_rv)
        val depDate: TextView = itemView.findViewById(R.id.departure_date_rv)
        val depTime: TextView = itemView.findViewById(R.id.depature_time_rv)
        val tripType: TextView = itemView.findViewById(R.id.trip_type_rv)

        init {
            rvMenu.setOnClickListener(this)


        }

        override fun onClick(v: View?) {
            if (adapterPosition != RecyclerView.NO_POSITION){
                listener.showMenu(rvMenu, tripList[adapterPosition])
            }
        }

    }

    interface OnItemClickListener {
        fun showMenu(view: View, trip: TripEntity)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
       val currentItem = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent,false)
        return (MyViewholder(currentItem))
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        val trip = tripList[position]
        holder.apply {
            dest.text = trip.destination
            destDate.text = trip.destination_date
            destTime.text = trip.destination_time
            dep.text = trip.depature
            depDate.text = trip.depature_date
            depTime.text = trip.depature_time

            tripType.apply {
                text = trip.trip_type

                backgroundTintList = when (text.toString()) {
                    "Business" -> ContextCompat.getColorStateList(this.context, R.color.business)
                    "Vacation" -> ContextCompat.getColorStateList(this.context, R.color.vacation)
                    "Education" -> ContextCompat.getColorStateList(this.context, R.color.education)
                    "Medical" -> ContextCompat.getColorStateList(this.context, R.color.medical)
                    else -> ContextCompat.getColorStateList(this.context, R.color.colorGreyText)

                }
            }

        }



    }

    override fun getItemCount(): Int {


        return tripList.size
    }


}