package hu.ait.thumbdrift.fragments

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import hu.ait.thumbdrift.R
import hu.ait.thumbdrift.adapter.RideAdapter
import hu.ait.thumbdrift.data.Ride
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment: Fragment() {

    companion object {
        const val TAG="SearchFragment"
        val KEY_USER_TO_SHOW = "KEY_USER_TO_SHOW"
    }

    lateinit var rideAdapter: RideAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView=inflater.inflate(R.layout.fragment_search,container,false)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        btnSearch.setOnClickListener{
//            Toast.makeText(context, "Filter Search", Toast.LENGTH_LONG).show()
//        }

        rideAdapter = RideAdapter(context!!, FirebaseAuth.getInstance().currentUser!!.uid)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerItem?.layoutManager = layoutManager

        recyclerItem?.adapter = rideAdapter

        initPosts()
    }

    private fun initPosts() {
        val db = FirebaseFirestore.getInstance()

        val query = db.collection("rides")

        var allRidesListener = query.addSnapshotListener(
            object: EventListener<QuerySnapshot> {
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