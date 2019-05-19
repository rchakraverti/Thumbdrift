package hu.ait.thumbdrift.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import hu.ait.thumbdrift.R
import hu.ait.thumbdrift.adapter.RideAdapter


class SearchFragment: Fragment() {

    companion object {
        const val TAG="SearchFragment"
        val KEY_USER_TO_SHOW = "KEY_USER_TO_SHOW"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView=inflater.inflate(R.layout.fragment_search,container,false)

        return rootView
    }

}