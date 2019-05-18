package hu.ait.thumbdrift.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.UserInfo
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.thumbdrift.R
import hu.ait.thumbdrift.data.UserProfile
import kotlinx.android.synthetic.main.fragment_edit_user_info.view.*
import kotlinx.android.synthetic.main.fragment_user_info.view.*

class UserProfileAdapter(
    private val context: Context,
    private val uId: String) : RecyclerView.Adapter<UserProfileAdapter.ViewHolder>() {

    private var postsList = mutableListOf<UserProfile>()
    private var postKeys = mutableListOf<String>()

    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_user_info, parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount() =  postsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (uid, name, gender, age, description, canDrive) =
            postsList[holder.adapterPosition]

        holder.tvName

        holder.tvName.text = name
        holder.tvDescription.text = description
        holder.tvAge.text.toString().toInt() = age
        holder.tvGender.text = gender


        if (uId == authorId) {
            holder.btnDeletePost.visibility = View.VISIBLE

            holder.btnDeletePost.setOnClickListener {
                removePost(holder.adapterPosition)
            }
        } else {
            holder.btnDeletePost.visibility = View.GONE
        }



        setAnimation(holder.itemView, position)
    }

    fun addUser(user: UserProfile, key: String) {
        postsList.add(user)
        postKeys.add(key)
        notifyDataSetChanged()
    }

//    private fun removePost(index: Int) {
//        FirebaseFirestore.getInstance().collection("posts").document(
//            postKeys[index]
//        ).delete()
//
//        postsList.removeAt(index)
//        postKeys.removeAt(index)
//        notifyItemRemoved(index)
//    }

    fun removePostByKey(key: String) {
        val index = postKeys.indexOf(key)
        if (index != -1) {
            postsList.removeAt(index)
            postKeys.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context,
                android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.tvName
        val tvDescription: TextView = itemView.tvDescription
        val tvGender: TextView = itemView.tvGender
        val tvAge: TextView = itemView.tvAge
        val ivCanDrive: ImageView = itemView.ivCanDrive

    }
}