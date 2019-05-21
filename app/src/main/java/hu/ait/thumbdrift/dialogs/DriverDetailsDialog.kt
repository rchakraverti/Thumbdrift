package hu.ait.thumbdrift.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.UserInfo
import hu.ait.thumbdrift.MainActivity
import hu.ait.thumbdrift.R
import hu.ait.thumbdrift.data.UserProfile
import hu.ait.thumbdrift.fragments.SearchFragment
import kotlinx.android.synthetic.main.driver_details_dialog.view.*

class DriverDetailsDialog : DialogFragment() {

    private lateinit var tvDriverName: TextView
    private lateinit var tvDriverDescription: TextView
    private lateinit var tvDriverAge: TextView
    private lateinit var tvDriverGender: TextView
    private lateinit var btnContactDriver: Button
    private lateinit var userToShow: UserProfile

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Driver details")

        userToShow = arguments?.getSerializable(
            SearchFragment.KEY_USER_TO_SHOW
        ) as UserProfile

        val rootView = requireActivity().layoutInflater.inflate(
            R.layout.driver_details_dialog, null
        )

        tvDriverName = rootView.tvDriverName
        tvDriverDescription = rootView.tvDriverDescription
        tvDriverAge = rootView.tvDriverAge
        tvDriverGender = rootView.tvDriverGender


        tvDriverName.text = userToShow.name
        tvDriverGender.text = userToShow.gender
        tvDriverAge.text = userToShow.age.toString()
        tvDriverDescription.text = userToShow.description

        builder.setView(rootView)

        builder.setPositiveButton("OK") {
                dialog, witch -> // empty
        }

        return builder.create()

        btnContactDriver.setOnClickListener {
            Toast.makeText(context as MainActivity, "Driver has been contacted.", Toast.LENGTH_LONG).show()
        }
    }
}