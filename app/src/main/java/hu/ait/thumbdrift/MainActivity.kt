package hu.ait.thumbdrift

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import hu.ait.thumbdrift.fragments.OfferRideFragment
import hu.ait.thumbdrift.fragments.SearchFragment
import hu.ait.thumbdrift.fragments.UserInfoFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.search -> {
                message.setText(R.string.title_home)
                showFragmentByTag(SearchFragment.TAG, true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.offerRide -> {
                message.setText(R.string.title_dashboard)
                showFragmentByTag(OfferRideFragment.TAG, true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.userInfo -> {
                message.setText(R.string.title_notifications)
                showFragmentByTag(UserInfoFragment.TAG, true)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun showFragmentByTag(tag: String, toBackStack: Boolean) {
        var fragment: Fragment? = supportFragmentManager.findFragmentByTag(tag)
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
}
