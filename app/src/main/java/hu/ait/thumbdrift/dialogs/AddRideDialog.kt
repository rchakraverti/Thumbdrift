package hu.ait.thumbdrift.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.CheckBox
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.thumbdrift.R
import hu.ait.thumbdrift.data.Ride
import hu.ait.thumbdrift.data.UserProfile
import hu.ait.thumbdrift.fragments.OfferRideFragment
import hu.ait.thumbdrift.fragments.UserInfoFragment
import kotlinx.android.synthetic.main.add_ride_dialog.*
import kotlinx.android.synthetic.main.add_ride_dialog.view.*
import kotlinx.android.synthetic.main.add_user_info_dialog.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import java.lang.RuntimeException

class AddRideDialog : DialogFragment() {

    interface RideHandler {
        fun rideCreated(ride: Ride)
        fun rideUpdated(ride: Ride)
    }

    private lateinit var rideHandler: RideHandler

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is RideHandler) {
            rideHandler = context
        } else {
            throw RuntimeException(
                "The activity does not implement the RideHandlerInterface")
        }
    }

    private lateinit var etOfferFrom: EditText
    private lateinit var etOfferTo: EditText
    private lateinit var etOfferDate: EditText
    private lateinit var etOfferSeats: EditText


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Add ride info")

        val rootView = requireActivity().layoutInflater.inflate(
            R.layout.add_ride_dialog, null)

        etOfferFrom = rootView.etOfferFrom
        etOfferTo = rootView.etOfferTo
        etOfferDate = rootView.etOfferDate
        etOfferSeats = rootView.etOfferSeats

        builder.setView(rootView)

        val arguments = this.arguments

        // IF I AM IN EDIT MODE
        if (arguments != null && arguments.containsKey(
                OfferRideFragment.KEY_RIDE_TO_EDIT)) {

            val ride = arguments.getSerializable(
                OfferRideFragment.KEY_RIDE_TO_EDIT
            ) as Ride

            etOfferFrom.setText(ride.from)
            etOfferTo.setText(ride.to)
            etOfferDate.setText(ride.date)
            etOfferSeats.setText(ride.seats)

            builder.setTitle("Edit user info")
        }

        builder.setPositiveButton("ADD") {
                dialog, witch -> // empty
        }

        return builder.create()
    }


    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            if (etOfferFrom.text.isNotEmpty()) {
                val arguments = this.arguments
                // IF EDIT MODE
                if (arguments != null && arguments.containsKey(UserInfoFragment.KEY_USER_TO_EDIT)) {
                    handleItemEdit()
                } else {
                    handleItemCreate()
                }
                dialog.dismiss()
            } else {
                etOfferFrom.error = "This field can not be empty"
            }
        }
    }

    private fun handleItemCreate() {

        rideHandler.rideCreated(
            Ride(null,
                FirebaseAuth.getInstance().currentUser!!.uid,
                etOfferFrom.text.toString(),
                etOfferTo.text.toString(),
                etOfferDate.text.toString(),
                etOfferSeats.text.toString().toInt()
            )
        )
    }

    private fun handleItemEdit() {
        val rideToEdit = arguments?.getSerializable(
            OfferRideFragment.KEY_RIDE_TO_EDIT
        ) as Ride

        rideToEdit.from = etOfferFrom.text.toString()
        rideToEdit.to = etOfferTo.text.toString()
        rideToEdit.date = etOfferDate.text.toString()
        rideToEdit.seats = etOfferSeats.text.toString().toInt()

        rideHandler.rideUpdated(rideToEdit)
    }
}