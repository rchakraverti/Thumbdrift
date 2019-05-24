package hu.ait.thumbdrift.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import hu.ait.thumbdrift.MainActivity
import hu.ait.thumbdrift.R
import hu.ait.thumbdrift.data.Ride
import hu.ait.thumbdrift.data.UserProfile
import hu.ait.thumbdrift.dialogs.AddUserInfoDialog
import kotlinx.android.synthetic.main.fragment_user_info.*
import kotlinx.android.synthetic.main.fragment_user_info.view.*


class UserInfoFragment : Fragment() {
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

        if (userProfile.canDrive) {
            ivCanDrive.setImageResource(R.drawable.candrive)
        } else {
            ivCanDrive.setImageResource(R.drawable.cannotdrive)
        }
    }

    public fun userProfileUpdated(userProfile: UserProfile) {
        var query = FirebaseFirestore.getInstance().collection(("userProfiles")).whereEqualTo(
            "uid", FirebaseAuth.getInstance().currentUser?.uid
        )

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
                                val profile = dc.document.toObject(UserProfile::class.java)
                                tvName.text = profile.name
                                tvDescription.text = profile.description
                                tvAge.text = profile.age.toString()
                                tvGender.text = profile.gender
                                if (profile!!.canDrive) {
                                    ivCanDrive.setImageResource(R.drawable.candrive)
                                } else {
                                    ivCanDrive.setImageResource(R.drawable.cannotdrive)
                                }

                            }
                            DocumentChange.Type.MODIFIED -> {
                                tvName.text = userProfile.name
                                tvDescription.text = userProfile.description
                                tvAge.text = userProfile.age.toString()
                                tvGender.text = userProfile.gender
                                if (userProfile!!.canDrive) {
                                    ivCanDrive.setImageResource(R.drawable.candrive)
                                } else {
                                    ivCanDrive.setImageResource(R.drawable.cannotdrive)
                                }

                            }
                            DocumentChange.Type.REMOVED -> {
                            }
                        }
                    }
                }
            })

    }

    companion object {
        const val TAG = "UserInfoFragment"
        val KEY_USER_TO_EDIT = "KEY_USER_TO_EDIT"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_user_info, container, false)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var query = FirebaseFirestore.getInstance().collection(("userProfiles")).whereEqualTo(
            "uid", FirebaseAuth.getInstance().currentUser?.uid
        )

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
                                val profile = dc.document.toObject(UserProfile::class.java)
                                tvName.text = profile.name
                                tvDescription.text = profile.description
                                tvAge.text = profile.age.toString()
                                tvGender.text = profile.gender
                                if (profile!!.canDrive) {
                                    ivCanDrive.setImageResource(R.drawable.candrive)
                                } else {
                                    ivCanDrive.setImageResource(R.drawable.cannotdrive)
                                }

                            }
                            DocumentChange.Type.MODIFIED -> {

                            }
                            DocumentChange.Type.REMOVED -> {
                            }
                        }
                    }
                }
            })


//        document.get().addOnCompleteListener(
//            object : OnCompleteListener<DocumentSnapshot> {
//                override fun onComplete(p0: Task<DocumentSnapshot>) {
//                    if (p0.isSuccessful) {
//                        var user = p0.result?.toObject(UserProfile::class.java)
//                        tvName.text = "Peter"
//                        tvDescription.text = "I am the best."
//                        tvAge.text = "20"
//                        tvGender.text = "M"
//                        ivCanDrive.setImageResource(R.drawable.candrive)
//
////                        tvName.text = user?.name
////                        Log.d("DEBUG", user?.name.toString())
////                        tvDescription.text = user?.description
////                        tvAge.text = user?.age.toString()
////                        tvGender.text = user?.gender
////                        if(user!!.canDrive) {
////                            ivCanDrive.setImageResource(R.drawable.candrive)
////                        }
////                        else {
////                            ivCanDrive.setImageResource(R.drawable.cannotdrive)
////                        }
//
//                    }
//                }
//            }

//        )

        btnEdit.setOnClickListener {
            AddUserInfoDialog().show(fragmentManager, "TAG_ITEM_DIALOG")
        }

        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            (context as MainActivity).finish()
        }
    }
}

