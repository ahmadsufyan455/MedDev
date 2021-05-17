package com.capstone.meddev.auth.register

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.meddev.data.User
import com.capstone.meddev.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.registerBtn.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val username = binding.usernameTxt.text.toString()
        val email = binding.emailTxt.text.toString()
        val password = binding.passwordTxt.text.toString()

        // check validation
        when {
            username.isEmpty() -> {
                binding.usernameTxt.error = "this field is required"
                binding.usernameTxt.requestFocus()
            }
            email.isEmpty() -> {
                binding.emailTxt.error = "this field is required"
                binding.emailTxt.requestFocus()
            }
            password.isEmpty() -> {
                binding.passwordTxt.error = "this field is required"
                binding.passwordTxt.requestFocus()
            }
            password.length < 6 -> {
                binding.passwordTxt.error = "password at least 6 character"
                binding.passwordTxt.requestFocus()
            }
            else -> {
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            // store data
                            val user = User(username, email)
                            db.collection("users")
                                .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                                .set(user)
                                .addOnSuccessListener {
                                    Log.d(
                                        "RegisterActivity",
                                        "DocumentSnapshot successfully written!"
                                    )
                                }
                                .addOnFailureListener { e ->
                                    Log.w("RegisterActivity", "Error writing document", e)
                                }
                            Toast.makeText(this, "Register successfully!", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Register failed!", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }
}