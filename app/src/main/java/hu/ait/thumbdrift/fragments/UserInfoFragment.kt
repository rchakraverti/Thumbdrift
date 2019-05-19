package hu.ait.thumbdrift.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.ait.thumbdrift.R
import hu.ait.thumbdrift.data.UserProfile
import kotlinx.android.synthetic.main.fragment_user_info.*


class UserInfoFragment: Fragment() {

    companion object {
        const val TAG="UserInfoFragment"
        val KEY_USER_TO_EDIT = "KEY_USER_TO_EDIT"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView=inflater.inflate(R.layout.fragment_user_info,container,false)

        btnEdit.setOnClickListener {





        }



        return rootView
    }

    var editIndex = -1


}

