package hu.ait.thumbdrift

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import hu.ait.thumbdrift.data.UserProfile
import hu.ait.thumbdrift.dialogs.AddUserInfoDialog
import hu.ait.thumbdrift.fragments.OfferRideFragment
import hu.ait.thumbdrift.fragments.SearchFragment
import hu.ait.thumbdrift.fragments.UserInfoFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AddUserInfoDialog.ProfileHandler {

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
}
