package hu.ait.thumbdrift.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.thumbdrift.MainActivity
import hu.ait.thumbdrift.R
import hu.ait.thumbdrift.data.UserProfile
import hu.ait.thumbdrift.dialogs.AddUserInfoDialog
import kotlinx.android.synthetic.main.fragment_user_info.*
import kotlinx.android.synthetic.main.fragment_user_info.view.*


class UserInfoFragment: Fragment() {
    public fun profileCreated(userProfile: UserProfile) {
        val db = FirebaseFirestore.getInstance().collection("userProfiles")
        db.add(userProfile).addOnSuccessListener {
            Toast.makeText(context as MainActivity, "Profile added.", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(context as MainActivity, "Error: ${it.message}", Toast.LENGTH_LONG).show()
        }

        tvName.text = userProfile.name
        tvDescription.text = userProfile.description
        tvAge.text = userProfile.age.toString()
        tvGender.text = userProfile.gender

        if(userProfile.canDrive) {
            ivCanDrive.setImageResource(R.drawable.candrive)
        }
        else {
            ivCanDrive.setImageResource(R.drawable.cannotdrive)
        }
    }

//    override fun userProfileUpdated(userProfile: UserProfile) {
//    }

    companion object {
        const val TAG="UserInfoFragment"
        val KEY_USER_TO_EDIT = "KEY_USER_TO_EDIT"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView=inflater.inflate(R.layout.fragment_user_info,container,false)

        rootView.btnEdit.setOnClickListener {

            //activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, UserInfoFragment())

           AddUserInfoDialog().show(fragmentManager, "TAG_ITEM_DIALOG")

        }
        return rootView
    }
}

