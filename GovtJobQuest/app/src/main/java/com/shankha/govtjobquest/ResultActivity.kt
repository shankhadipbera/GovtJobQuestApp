package com.shankha.govtjobquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.shankha.govtjobquest.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private val binding : ActivityResultBinding by lazy{
        ActivityResultBinding.inflate(layoutInflater)
    }
    val database = FirebaseDatabase.getInstance()
    val databaseReference = database.reference.child("Scores")
    val auth =FirebaseAuth.getInstance()
    val user = auth.currentUser
    var userCorrect =""
    var userWrong =""
    var userAccurecy: Float = 0F
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.gradient_bar))

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                user?.let{
                    val userUid =it.uid
                    userCorrect= snapshot.child(userUid).child("Correct Answer").value.toString()
                    userWrong =snapshot.child(userUid).child("Wrong Answer").value.toString()
                    userAccurecy= ((userCorrect.toFloat()/(userCorrect.toFloat()+userWrong.toFloat()))*100)
                    binding.correctAns.text=userCorrect
                    binding.wrongAns.text=userWrong
                    binding.Accurecy.text=userAccurecy.toString()+" %"
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        binding.btnPlayAgain.setOnClickListener {

            finish()

        }
        binding.btnExit.setOnClickListener {
            startActivity(Intent(this@ResultActivity,MainActivity::class.java))
            finish()
        }


    }
}