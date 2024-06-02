package com.shankha.govtjobquest

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.identity.SignInPassword
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.shankha.govtjobquest.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }
    val auth :FirebaseAuth =FirebaseAuth.getInstance()
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var  activityResultLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val googleBtnText = binding.googleBtnSignIn.getChildAt(0) as TextView
        googleBtnText .text = "Continue With Google"
        googleBtnText.setTextColor(Color.BLACK)
        googleBtnText.textSize =18F

        registerActivityForGoogleSignIn()

        binding.buttonSignin.setOnClickListener {
            val Uemail = binding.editTextEmail.text.toString().trim()
            val Upassword= binding.editTextPassword.text.toString().trim()
            if(Uemail.isEmpty()|| Upassword.isEmpty()){
                Toast.makeText(applicationContext,"Please Fill All The Details",Toast.LENGTH_SHORT).show()
            }else{
                SignInUser(Uemail,Upassword)
            }


        }

        binding.googleBtnSignIn.setOnClickListener{
            signInGoogle()

        }

        binding.dontHaveAccount.setOnClickListener {

            startActivity(Intent(this,SignUpActivity::class.java))
        }

        binding.forgetPassword.setOnClickListener {
            startActivity(Intent(this,ForgetPasswordActivity::class.java))

        }

    }



    override fun onStart() {
        super.onStart()
        val user =auth.currentUser
        if(user!=null){
            Toast.makeText(applicationContext,"Welcome to GovtJobQuest ", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }



    fun SignInUser(email : String , password : String){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(applicationContext,"Welcome to GovtJobQuest ", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(applicationContext,task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun signInGoogle() {
       val gso =GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
           .requestIdToken("408116328081-1m465jje8qgf1hi0353b61kodppb2mgp.apps.googleusercontent.com")
           .requestEmail().build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)

        signIn()
    }

    private fun signIn() {
        val signInIntent :Intent = googleSignInClient.signInIntent
        activityResultLauncher.launch(signInIntent)
    }
    private fun registerActivityForGoogleSignIn(){
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {result ->  
                val resultCode =result.resultCode
                val data = result.data
                if(resultCode== RESULT_OK && data!=null){
                    val task :Task<GoogleSignInAccount>  = GoogleSignIn.getSignedInAccountFromIntent(data)
                    firebaseSignInWithGoogle(task)
                }
            })
    }

    private fun firebaseSignInWithGoogle(task: Task<GoogleSignInAccount>) {
        try {
            val account:GoogleSignInAccount =task.getResult(ApiException::class.java)
            Toast.makeText(applicationContext,"Welcome to GovtJobQuest ", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
            firebaseGoogleAccount(account)
        }catch (e : ApiException){
            Toast.makeText(applicationContext,e.localizedMessage, Toast.LENGTH_SHORT).show()
        }


    }

    private fun firebaseGoogleAccount(account: GoogleSignInAccount) {
        val authCredential =GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(authCredential).addOnCompleteListener {task->
            if(task.isSuccessful){
                   // val user =auth.currentUser

            }else{

            }

        }

    }

}