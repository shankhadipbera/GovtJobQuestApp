package com.shankha.govtjobquest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.shankha.govtjobquest.databinding.ActivityForgetPasswordBinding

class ForgetPasswordActivity : AppCompatActivity() {
    private val binding : ActivityForgetPasswordBinding by lazy{
        ActivityForgetPasswordBinding.inflate(layoutInflater)
    }
    val auth =FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.gradient_bar))

        binding.buttonForget.setOnClickListener {
            val email= binding.ForgetEmail.text.toString().trim()
            if(!email.isEmpty()){
                auth.sendPasswordResetEmail(email).addOnCompleteListener {task ->
                    if(task.isSuccessful){
                        Toast.makeText(this,"Password Reset Mail is sent to your Email Address",Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this,task.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this,"Please Enter Your Email",Toast.LENGTH_SHORT).show()
            }

        }
    }
}