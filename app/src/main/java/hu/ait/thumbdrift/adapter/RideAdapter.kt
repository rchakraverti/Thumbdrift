package hu.ait.thumbdrift.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.thumbdrift.R
import hu.ait.thumbdrift.data.Ride
import kotlinx.android.synthetic.main.ride_row.view.*
import java.util.Collections.list

class RideAdapter(private val context: Context, private val uID: String) :
    RecyclerView.Adapter<RideAdapter.ViewHolder>() {

    private var rideList = mutableListOf<Ride>()
    private var rideKeys = mutableListOf<String>()

    private var lastPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.ride_row, parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount() = rideList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (uid, authorUID, from, to, date, seats) =
            rideList[holder.adapterPosition]

        holder.tvFrom.text = "From: $from"
        holder.tvTo.text = "To: $to"
        holder.tvDate.text = "Date: $date"
        holder.tvNumberOfSeats.text = "Seats Avaiable: ${seats.toString()}"
        //var s = Integer.parseInt(holder.tvNumberOfSeats.text.toString())
        //s = seats
        holder.btnDriverDetails.setOnClickListener {
            //open author information based on their author id: driver details fragment
            }

        if (uID == uid) {
            holder.btnDelete.visibility = View.VISIBLE

            holder.btnDelete.setOnClickListener {
                removeRide(holder.adapterPosition)
            }
        } else {
            holder.btnDelete.visibility = View.GONE
        }
    }

    fun updateRide(ride: Ride, editIndex: Int) {
        rideList.set(editIndex, ride)
        notifyItemChanged(editIndex)
    }

    fun addRide(ride: Ride, key: String) {
        rideList.add(ride)
        rideKeys.add(key)
        notifyDataSetChanged()
    }

    private fun removeRide(index: Int) {
        FirebaseFirestore.getInstance().collection("rides").document(
            rideKeys[index]
        ).delete()

        rideList.removeAt(index)
        rideKeys.removeAt(index)
        notifyItemRemoved(index)
    }

    fun removePostByKey(key: String) {
        val index = rideKeys.indexOf(key)
        if (index != -1) {
            rideList.removeAt(index)
            rideKeys.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun clearAll() {
        rideList.clear()
        rideKeys.clear()
        notifyDataSetChanged()
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFrom: TextView = itemView.tvFrom
        val tvTo: TextView = itemView.tvTo
        val tvDate: TextView = itemView.tvDate
        val tvNumberOfSeats: TextView = itemView.tvNumberOfSeats
        val btnDriverDetails: Button = itemView.btnDriverDetails
        val btnDelete: Button = itemView.btnDelete

    }
}