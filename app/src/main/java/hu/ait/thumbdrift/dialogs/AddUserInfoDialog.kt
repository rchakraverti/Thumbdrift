package hu.ait.thumbdrift.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.CheckBox
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.thumbdrift.R
import hu.ait.thumbdrift.data.UserProfile
import hu.ait.thumbdrift.fragments.UserInfoFragment
import kotlinx.android.synthetic.main.add_user_info_dialog.view.*
import java.lang.RuntimeException

class AddUserInfoDialog : DialogFragment() {



    interface ProfileHandler {
        fun userProfileCreated(userProfile: UserProfile)
        fun userProfileUpdated(userProfile: UserProfile)
    }

    private lateinit var userHandler: ProfileHandler

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is ProfileHandler) {
            userHandler = context
        } else {
            throw RuntimeException(
                "The activity does not implement the ProfileHandlerInterface")
        }
    }

    private lateinit var etName: EditText
    private lateinit var etDescription: EditText
    private lateinit var etAge: EditText
    private lateinit var etGender: EditText
    private lateinit var cbCanDrive: CheckBox


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Add user info")

        val rootView = requireActivity().layoutInflater.inflate(
            R.layout.add_user_info_dialog, null
        )

        etName = rootView.etName
        etAge = rootView.etAge
        etGender = rootView.etGender
        etDescription = rootView.etDescription
        cbCanDrive = rootView.cbCanDrive

        builder.setView(rootView)



        val arguments = this.arguments

        // IF I AM IN EDIT MODE
        if (arguments != null && arguments.containsKey(
                UserInfoFragment.KEY_USER_TO_EDIT)) {

            val user = arguments.getSerializable(
                UserInfoFragment.KEY_USER_TO_EDIT
            ) as UserProfile

            etName.setText(user.name)
            etDescription.setText(user.description)
            etAge.setText(user.age.toString())
            etGender.setText(user.gender)
            cbCanDrive.isChecked = user.canDrive



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
            if (etName.text.isNotEmpty()) {
                val arguments = this.arguments
                // IF EDIT MODE
                if (arguments != null && arguments.containsKey(UserInfoFragment.KEY_USER_TO_EDIT)) {
                    handleItemEdit()
                } else {
                    handleItemCreate()
                }

                dialog.dismiss()
            } else {
                etName.error = "This field can not be empty"
            }
        }
    }

    private fun handleItemCreate() {


        userHandler.userProfileCreated(
            UserProfile(null,
                etName.toString(),
                etGender.toString(),
                etAge.toString().toInt(),
                etDescription.toString(),
                cbCanDrive.isChecked
            )
        )

        val db = FirebaseFirestore.getInstance().collection("userProfiles")

        db.add(UserProfile(null,
            etName.toString(),
            etGender.toString(),
            etAge.toString().toInt(),
            etDescription.toString(),
            cbCanDrive.isChecked
        )).addOnSuccessListener {

        }.addOnFailureListener {

        }
        }



    private fun handleItemEdit() {
        val profileToEdit = arguments?.getSerializable(
            UserInfoFragment.KEY_USER_TO_EDIT
        ) as UserProfile

        profileToEdit.description = etDescription.text.toString()
        profileToEdit.name = etName.text.toString()
        profileToEdit.gender = etGender.text.toString()
        profileToEdit.canDrive = cbCanDrive.isChecked
        profileToEdit.age = etAge.text.toString().toInt()


        userHandler.userProfileUpdated(profileToEdit)
    }

}