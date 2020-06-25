package com.admin.busmartappadmin2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*


class SignupActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()


        btnSignUp.setOnClickListener {
            val username = txtSignUpUsername.text.toString()
            val password = txtSignUpPassword.text.toString()

            if (password.length < 5) {
                Toast.makeText(
                    this, "Password should be greater than 5 characters.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            if (username.isNotEmpty() && password.isNotEmpty()) {
                mAuth!!.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = mAuth!!.currentUser
                            finish()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                this, "Sign up failed. ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        btnSignUp.isClickable = true
                    }
                    .addOnFailureListener {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            this, "Sign up failed. ${it.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        btnSignUp.isClickable = true
                    }

            }

        }
    }
}
