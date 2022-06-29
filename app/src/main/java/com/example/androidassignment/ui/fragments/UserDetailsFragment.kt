package com.example.androidassignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.androidassignment.R
import com.example.androidassignment.model.UserInfo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user_details.*

class UserDetailsFragment : Fragment(R.layout.fragment_user_details) {
    private lateinit var user: UserInfo
    private lateinit var userName: TextView
    private lateinit var userImageView: ImageView
    private lateinit var userCountdownBirthday: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_details, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userName = detailUserName
        userImageView = detailUserImageView
        userCountdownBirthday = detailUserCountdownBirthday

        displayUserDetails(user)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = this.arguments?.get("currentUser") as UserInfo
    }

    fun displayUserDetails(user:UserInfo){
        userName.text = "Name: ${user.name.first} ${user.name.last}"
        Picasso.get()
            .load(user.picture.large)
            .placeholder(R.mipmap.ic_launcher)
            .into(userImageView)
        userCountdownBirthday.text = "Age: ${user.dob.age}"
    }
}