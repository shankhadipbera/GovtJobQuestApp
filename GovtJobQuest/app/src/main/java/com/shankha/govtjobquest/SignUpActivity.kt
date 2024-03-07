package com.shankha.govtjobquest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.shankha.govtjobquest.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private val binding:ActivitySignUpBinding by lazy{
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    val auth: FirebaseAuth =FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.gradient_bar))

        binding.buttonSignUp.setOnClickListener {

            val email= binding.SignUpEmail.text.toString().trim()
            val password =binding.SignUpPassword.text.toString().trim()
            if(email.isEmpty()|| password.isEmpty()){
                Toast.makeText(applicationContext,"Please Fill All The Details",Toast.LENGTH_SHORT).show()
            }else{
                signUpWithFirebase(email,password)
            }


        }
    }

    private fun signUpWithFirebase(email: String, password: String) {
        binding.progressBar.visibility= View.VISIBLE
        binding.buttonSignUp.isClickable=false
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener   { task ->
            binding.progressBar.visibility= View.INVISIBLE
            binding.buttonSignUp.isClickable=true
            if(task.isSuccessful){
                Toast.makeText(applicationContext,"Your Account Created Successfully",Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(applicationContext,task.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
            }

        }
    }

}