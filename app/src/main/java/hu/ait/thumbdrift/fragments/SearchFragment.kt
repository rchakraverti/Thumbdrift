package hu.ait.thumbdrift.fragments

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import hu.ait.thumbdrift.R
import hu.ait.thumbdrift.adapter.RideAdapter
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
}