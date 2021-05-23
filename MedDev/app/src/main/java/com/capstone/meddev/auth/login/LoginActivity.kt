package com.capstone.meddev.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.meddev.auth.register.RegisterActivity
import com.capstone.meddev.dashboard.DashboardActivity
import com.capstone.meddev.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.loginBtn.setOnClickListener {
            login()
        }

        binding.signUpTb.setOnClickListener {
            moveToRegisterActivity()
        }
    }

    private fun moveToRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun login() {

        val email = binding.emailTxt.text.toString()
        val password = binding.passwordTxt.text.toString()

        when {
            email.isEmpty() -> {
                binding.emailTxt.error = "Email cannot be empty"
                binding.emailTxt.requestFocus()
            }
            password.isEmpty() -> {
                binding.passwordTxt.error = "Password cannot be empty"
                binding.passwordTxt.requestFocus()
            }
            password.length < 6 -> {
                binding.passwordTxt.error = "Password too short"
                binding.passwordTxt.requestFocus()
            }
            else -> {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
                            moveToDashboard()
                        } else {
                            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }

    private fun moveToDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}