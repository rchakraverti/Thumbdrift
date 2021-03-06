package hu.ait.thumbdrift.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import hu.ait.thumbdrift.MainActivity
import hu.ait.thumbdrift.R
import hu.ait.thumbdrift.adapter.RideAdapter
import hu.ait.thumbdrift.data.Ride
import hu.ait.thumbdrift.data.UserProfile
import hu.ait.thumbdrift.dialogs.AddRideDialog
import hu.ait.thumbdrift.dialogs.AddUserInfoDialog
import kotlinx.android.synthetic.main.fragment_offer_ride.*
import kotlinx.android.synthetic.main.fragment_offer_ride.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_user_info.*
import kotlinx.android.synthetic.main.ride_row_edit_delete.*

class OfferRideFragment: Fragment() {

    companion object {
        const val TAG = "OfferRideFragment"
        val KEY_RIDE_TO_EDIT = "KEY_USER_TO_EDIT"
    }

    lateinit var rideAdapter: RideAdapter

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

    public fun rideUpdated(ride: Ride) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_offer_ride, container, false)

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

        rideAdapter = RideAdapter(context!!, FirebaseAuth.getInstance().currentUser!!.uid, "OFFER")

        val layoutManager = LinearLayoutManager(context)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        view!!.recyclerOffer?.layoutManager = layoutManager

        view!!.recyclerOffer.adapter = rideAdapter

        initPosts()
    }

    private fun initPosts() {

        val query = FirebaseFirestore.getInstance().collection(("rides"))
            .whereEqualTo("authorUID", FirebaseAuth.getInstance().currentUser!!.uid)


        var allRidesListener = query.addSnapshotListener(
            object : EventListener<QuerySnapshot> {
                override fun onEvent(querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {
                    if (e != null) {
                        Toast.makeText(context, "listen error: ${e.message}", Toast.LENGTH_LONG).show()
                        return
                    }

                    for (dc in querySnapshot!!.documentChanges) {
                        when (dc.getType()) {
                            DocumentChange.Type.ADDED -> {
                                val ride = dc.document.toObject(Ride::class.java)
                                rideAdapter.addRide(ride, dc.document.id)
                            }
                            DocumentChange.Type.MODIFIED -> {
                                Toast.makeText(context, "update: ${dc.document.id}", Toast.LENGTH_LONG).show()
                            }
                            DocumentChange.Type.REMOVED -> {
                                rideAdapter.removePostByKey(dc.document.id)
                            }
                        }
                    }
                }
            })
    }
}



//        btnOfferRide.setOnClickListener {
//            sendClick()
//        }
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

