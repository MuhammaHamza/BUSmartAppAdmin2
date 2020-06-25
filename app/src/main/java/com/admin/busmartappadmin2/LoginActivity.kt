package com.admin.busmartappadmin2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {

            if (txtLoginUsername.text.toString().isNotEmpty()
                && txtLoginPassword.text.toString().isNotEmpty()
            ) {
                btnLogin.isClickable = false
                mAuth!!.signInWithEmailAndPassword(
                    txtLoginUsername.text.toString(),
                    txtLoginPassword.text.toString()
                )
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        if (task.isSuccessful) {
                            finish()
                            val user = mAuth!!.currentUser
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                this, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                            btnLogin.isClickable = true
                        }
                    }
                    .addOnFailureListener {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        btnLogin.isClickable = true
                    }

            }

        }


        btnLoginSignUp.setOnClickListener {
            finish()
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        }
    }
}
