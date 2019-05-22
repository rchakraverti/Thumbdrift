package hu.ait.thumbdrift.adapter

import android.content.Context
import android.nfc.Tag
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.thumbdrift.MainActivity
import hu.ait.thumbdrift.R
import hu.ait.thumbdrift.data.Ride
import hu.ait.thumbdrift.dialogs.DriverDetailsDialog
import kotlinx.android.synthetic.main.ride_row.view.*
import kotlinx.android.synthetic.main.ride_row.view.btnDriverDetails
import kotlinx.android.synthetic.main.ride_row.view.tvDate
import kotlinx.android.synthetic.main.ride_row.view.tvFrom
import kotlinx.android.synthetic.main.ride_row.view.tvNumberOfSeats
import kotlinx.android.synthetic.main.ride_row.view.tvTo
import kotlinx.android.synthetic.main.ride_row_edit_delete.view.*
import java.sql.Driver
import java.util.Collections.list

class RideAdapter(private val context: Context, private val uID: String, private val tag: String) :
    RecyclerView.Adapter<RideAdapter.ViewHolder>() {

    private var rideList = mutableListOf<Ride>()
    private var rideKeys = mutableListOf<String>()

    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.ride_row_edit_delete, parent, false
        )

        if(tag == "SEARCH") {
            view.btnDelete.visibility = View.GONE
            //view.btnEdit.visibility = View.GONE

        }
        else {
            view.btnDriverDetails.visibility = View.GONE
            //view.btnEdit.visibility = View.VISIBLE
        }
        return ViewHolder(view)
    }

    override fun getItemCount() = rideList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ride =
            rideList[holder.adapterPosition]

        holder.tvFrom.text = ride.from
        holder.tvTo.text = ride.to
        holder.tvDate.text = ride.date
        holder.tvNumberOfSeats.text = ride.seats.toString()
        //var s = Integer.parseInt(holder.tvNumberOfSeats.text.toString())
        //s = seats
        holder.btnDriverDetails.setOnClickListener {
            //open author information based on their author id: driver details fragment
            Toast.makeText(context, "Driver details", Toast.LENGTH_LONG).show()
            (context as MainActivity).showDriverDetails(ride.authorUID, holder.adapterPosition)
            //DriverDetailsDialog().show(fragmentManager, "TAG_ITEM_DIALOG")
            }
        holder.btnDelete.setOnClickListener {
            removeRide(holder.adapterPosition)
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

    fun removeRide(index: Int) {
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
        //val btnEdit: Button = itemView.btnEdit
      }
}