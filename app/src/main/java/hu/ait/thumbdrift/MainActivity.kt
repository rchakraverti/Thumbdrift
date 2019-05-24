package hu.ait.thumbdrift

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.thumbdrift.data.Ride
import hu.ait.thumbdrift.data.UserProfile
import hu.ait.thumbdrift.dialogs.AddRideDialog
import hu.ait.thumbdrift.dialogs.AddUserInfoDialog
import hu.ait.thumbdrift.dialogs.DriverDetailsDialog
import hu.ait.thumbdrift.fragments.OfferRideFragment
import hu.ait.thumbdrift.fragments.SearchFragment
import hu.ait.thumbdrift.fragments.UserInfoFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AddUserInfoDialog.ProfileHandler, AddRideDialog.RideHandler {
    override fun rideUpdated(ride: Ride) {
        if (fragment is OfferRideFragment){
            (fragment as OfferRideFragment).rideUpdated(ride)
        }
    }

    override fun rideCreated(ride: Ride) {
        if (fragment is OfferRideFragment){
            (fragment as OfferRideFragment).rideCreated(ride)
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.search -> {
                showFragmentByTag(SearchFragment.TAG, true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.offerRide -> {
                showFragmentByTag(OfferRideFragment.TAG, true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.userInfo -> {
                showFragmentByTag(UserInfoFragment.TAG, true)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    var fragment: Fragment? = null

    private fun showFragmentByTag(tag: String, toBackStack: Boolean) {
        fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            if (SearchFragment.TAG == tag) {
                fragment = SearchFragment()
            } else if (OfferRideFragment.TAG == tag) {
                fragment = OfferRideFragment()
            } else if (UserInfoFragment.TAG == tag) {
                fragment = UserInfoFragment()
            }
        }

        if (fragment != null) {
            val ft = supportFragmentManager
                .beginTransaction()
            ft.replace(R.id.fragmentContainer, fragment!!, tag)
            if (toBackStack) {
                ft.addToBackStack(null)
            }
            ft.commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        showFragmentByTag(SearchFragment.TAG, false)

    }

    override fun userProfileCreated(userProfile: UserProfile){
        if (fragment is UserInfoFragment){
            (fragment as UserInfoFragment).profileCreated(userProfile)
        }
    }

    override fun userProfileUpdated(userProfile: UserProfile) {
        if (fragment is UserInfoFragment){
            (fragment as UserInfoFragment).userProfileUpdated(userProfile)
        }
    }

    fun showDriverDetails(authorUID: String, adapterPosition: Int) {
        val showDetails = DriverDetailsDialog()

        var document = FirebaseFirestore.getInstance().collection(("userProfiles")).document(
            authorUID
        )
        document.get().addOnCompleteListener(
            object : OnCompleteListener<DocumentSnapshot> {
                override fun onComplete(p0: Task<DocumentSnapshot>) {
                    if (!p0.isSuccessful){
                        var user= p0.result?.toObject(UserProfile::class.java)
                        val bundle = Bundle()
                        bundle.putSerializable("KEY_ITEM_TO_SHOW", user)
                        showDetails.show(supportFragmentManager, "SHOWDETAILSDIALOG")


                    }
                }

            }
        )
    }
}
