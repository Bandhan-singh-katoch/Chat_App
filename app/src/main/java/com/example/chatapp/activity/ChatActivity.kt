package com.example.chatapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.chatapp.R
import com.example.chatapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_users.imgProfile

class ChatActivity : AppCompatActivity() {
    var firebaseUser:FirebaseUser? = null
    var reference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        imgBack.setOnClickListener {
            onBackPressed()
        }

        var intent = getIntent()
        var userId = intent.getStringExtra("userId")

        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)

        reference!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                tvUserName.text = user!!.userName

                if(user.profileImage == ""){
                    imgProfile.setImageResource(R.drawable.proflie_img)
                }else{
                    Glide.with(this@ChatActivity).load(user.profileImage).into(imgProfile)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}