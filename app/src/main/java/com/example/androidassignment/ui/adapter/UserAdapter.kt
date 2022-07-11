package com.example.androidassignment.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment.R
import com.example.androidassignment.model.UserInfo
import com.squareup.picasso.Picasso

class UserAdapter(var userList: MutableList<UserInfo>, val onUserClickListener: OnUserClickListener): RecyclerView.Adapter<UserAdapter.ViewHolder>(){

    //ViewHolder will display each user in our userList
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val userName = itemView.findViewById<TextView>(R.id.userName)
        val userImage = itemView.findViewById<ImageView>(R.id.userImageView)
        val userEmail = itemView.findViewById<TextView>(R.id.userEmail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //to inflate user_item to ViewGroup object
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false))
    }

    //here we setting the data in the entery fragment from our User class
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val specificUser: UserInfo = userList[position]

        holder.userName.text = "${specificUser.name.first} ${specificUser.name.last}"
        holder.userEmail.text = specificUser.email
        Picasso.get()
            .load(specificUser.picture.large)
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.userImage)

        //ViewCompat - helper for accessing features in View.
        //GOAL - identify userImage by the user name
        ViewCompat.setTransitionName(holder.userImage, "${specificUser.name.first} ${specificUser.name.last}")

        holder.itemView.setOnClickListener {
            onUserClickListener.onUserClickListener(specificUser, holder.userImage)
        }
    }

    override fun getItemCount(): Int = userList.size

    interface OnUserClickListener{
        fun onUserClickListener(results: UserInfo, sharedImageView: ImageView)
    }

    fun setItems(list: List<UserInfo>) {
        userList.clear()
        userList.addAll(list)

        //Don't remove notifyDataSetChanged() - pavel checked and said its necessary
        notifyDataSetChanged()
    }
}