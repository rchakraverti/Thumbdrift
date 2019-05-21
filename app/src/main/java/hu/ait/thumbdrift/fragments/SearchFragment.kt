package hu.ait.thumbdrift.fragments

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import hu.ait.thumbdrift.R
import hu.ait.thumbdrift.adapter.RideAdapter
import hu.ait.thumbdrift.data.Ride
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*


class SearchFragment : Fragment() {

    companion object {
        const val TAG = "SearchFragment"
        val KEY_USER_TO_SHOW = "KEY_USER_TO_SHOW"
    }

    lateinit var rideAdapter: RideAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)

        return rootView
    }

    private fun filterSearch() {
        rideAdapter.clearAll()

        val db = FirebaseFirestore.getInstance()
        var query: Query = db.collection("rides")
        if (!etFrom.text.toString().isEmpty()) {
            query = query.whereEqualTo("from", etFrom.text.toString())
        }
        if (!etTo.text.toString().isEmpty()) {
            query = query.whereEqualTo("to", etTo.text.toString())
        }
        if (!etDate.text.toString().isEmpty()) {
            query = query.whereEqualTo("date", etDate.text.toString())
        }

        var ridesListener = query.addSnapshotListener(
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rideAdapter = RideAdapter(context!!, FirebaseAuth.getInstance().currentUser!!.uid, "SEARCH")

        val layoutManager = LinearLayoutManager(context)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerItem?.layoutManager = layoutManager

        recyclerItem?.adapter = rideAdapter

        initPosts()

        btnSearch?.setOnClickListener {
            filterSearch()
        }


    }

    private fun initPosts() {
        val db = FirebaseFirestore.getInstance()

        val query = db.collection("rides")

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