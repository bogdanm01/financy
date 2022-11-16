package com.example.financy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class SignInActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var txtSwitchToRegister: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        txtSwitchToRegister = findViewById(R.id.login_bottom_text)
        txtSwitchToRegister.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.login_bottom_text -> {startActivity(Intent(this, SignUpActivity::class.java))}
        }
    }

}