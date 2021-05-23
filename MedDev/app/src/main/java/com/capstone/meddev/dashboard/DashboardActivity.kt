package com.capstone.meddev.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.meddev.auth.login.LoginActivity
import com.capstone.meddev.databinding.ActivityDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUsername()

        binding.btnLogout.setOnClickListener { logout() }
    }

    private fun getUsername() {
        val user = FirebaseAuth.getInstance().currentUser
        db = FirebaseFirestore.getInstance()
        val userId = user?.uid
        if (userId != null) {
            db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val username = documentSnapshot.getString("username")
                    binding.tvUsername.text = username
                }
        }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        Toast.makeText(this, "You're logout", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }
}