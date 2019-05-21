package hu.ait.thumbdrift.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.thumbdrift.MainActivity
import hu.ait.thumbdrift.R
import hu.ait.thumbdrift.data.Ride
import hu.ait.thumbdrift.data.UserProfile
import hu.ait.thumbdrift.dialogs.AddRideDialog
import hu.ait.thumbdrift.dialogs.AddUserInfoDialog
import kotlinx.android.synthetic.main.fragment_offer_ride.*
import kotlinx.android.synthetic.main.fragment_offer_ride.view.*
import kotlinx.android.synthetic.main.fragment_user_info.*

class OfferRideFragment: Fragment() {

    companion object {
        const val TAG="OfferRideFragment"
        val KEY_RIDE_TO_EDIT = "KEY_USER_TO_EDIT"
    }

    public fun rideCreated(ride: Ride) {
        var ridesCollection = FirebaseFirestore.getInstance().collection(
            "rides"
        )

        ridesCollection.add(ride).addOnSuccessListener {
            Toast.makeText(context, "Ride saved", Toast.LENGTH_LONG).show()

        }.addOnFailureListener {
            Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView=inflater.inflate(R.layout.fragment_offer_ride,container,false)

        rootView.btnOfferRide.setOnClickListener {
            AddRideDialog().show(fragmentManager, "TAG_ITEM_DIALOG")

        }


        return rootView
    }

    fun sendClick() {
       // uploadPost()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        btnOfferRide.setOnClickListener {
//            sendClick()
//        }
    }

//    fun uploadPost() {
//        val ride = Ride(
//            FirebaseAuth.getInstance().currentUser!!.uid,
//            FirebaseAuth.getInstance().currentUser!!.displayName!!,
//            etOfferFrom.text.toString(),
//            etOfferTo.text.toString(),
//            etOfferDate.text.toString(),
//            etOfferSeats.text.toString().toInt()
//
//        )
//
//        var ridesCollection = FirebaseFirestore.getInstance().collection(
//            "rides"
//        )
//
//        ridesCollection.add(ride).addOnSuccessListener {
//            Toast.makeText(context, "Ride saved", Toast.LENGTH_LONG).show()
//
//        }.addOnFailureListener {
//            Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_LONG).show()
//        }
//    }

}