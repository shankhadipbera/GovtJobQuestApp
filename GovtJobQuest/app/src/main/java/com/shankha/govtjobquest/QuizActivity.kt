package com.shankha.govtjobquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.shankha.govtjobquest.ads.AppOpenAdsManager
import com.shankha.govtjobquest.ads.RewardedAdsManager
import com.shankha.govtjobquest.databinding.ActivityQuizBinding
import kotlin.random.Random

class QuizActivity : AppCompatActivity() {
    private val binding: ActivityQuizBinding by lazy {
        ActivityQuizBinding.inflate(layoutInflater)
    }
    lateinit var parents: String
    lateinit var children: String
    val database = FirebaseDatabase.getInstance()
    var question = ""
    var option1 = ""
    var option2 = ""
    var option3 = ""
    var option4 = ""
    var answer = ""
    var questionCount = 0
    var questionNo = 0
    var userAnswer = ""
    var userCAnswer = 0
    var userWAnswer = 0
    lateinit var timer: CountDownTimer
    private var totalTime = 15000L
    var timerContinue = false
    var leftTime = totalTime
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val scoreRef = database.reference
    var noOFQuestion = 0
    var timePerQuestion = 0
    val questionSet = HashSet<Int>()
    private lateinit var rewardedAdsManager: RewardedAdsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.gradient_bar))

        MobileAds.initialize(this) {}
        rewardedAdsManager = RewardedAdsManager(this)

        title = "   " + intent.getStringExtra("title")

        binding.continuetBtn.setOnClickListener {
            val x = binding.QuestionNoIp.text.toString()
            val y = binding.QuestionTimeIP.text.toString()
            if (x.isNotEmpty() && y.isNotEmpty()) {
                noOFQuestion = x.toInt()
                timePerQuestion = y.toInt()
                if (noOFQuestion in 1..24 && timePerQuestion in 10..120) {
                    totalTime = (timePerQuestion * 1000).toLong()
                    leftTime =totalTime
                    rewardedAdsManager.showRewardedAd()
                    binding.lodaingTV.visibility = View.VISIBLE
                    binding.continuetBtn.isEnabled = false
                    binding.continuetBtn.visibility = View.INVISIBLE
                    binding.startLinearLayout.visibility = View.INVISIBLE
                    android.os.Handler(Looper.getMainLooper()).postDelayed({
                        binding.startBtn.visibility = View.VISIBLE
                        binding.startBtn.isClickable = true
                        binding.lodaingTV.visibility = View.INVISIBLE
                    }, 10000)
                } else {
                    Toast.makeText(this, "Please enter proper values", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.startBtn.setOnClickListener {
            startQuiz()
            binding.startBtn.isEnabled = false
            binding.startBtn.visibility = View.INVISIBLE
        }
        binding.btnNext.setOnClickListener {
            resetTimer()
            gameLogic()
        }
        binding.btnFinish.setOnClickListener {
            sendScore()
        }
        binding.Option1.setOnClickListener {
            pauseTimer()
            userAnswer = "a"
            if (answer == userAnswer) {
                // binding.Option1.setBackgroundColor(Color.GREEN)
                binding.Option1.background = resources.getDrawable(R.drawable.right_back)
                userCAnswer++
                binding.correctAnswer.text = userCAnswer.toString()
            } else {
                binding.Option1.background = resources.getDrawable(R.drawable.wrong_back)
                userWAnswer++
                binding.wrongAnswer.text = userWAnswer.toString()
                findAnswer()
            }
            disableClickableOfOption()
        }
        binding.Option2.setOnClickListener {
            pauseTimer()
            userAnswer = "b"
            if (answer == userAnswer) {
                binding.Option2.background = resources.getDrawable(R.drawable.right_back)
                userCAnswer++
                binding.correctAnswer.text = userCAnswer.toString()
            } else {
                binding.Option2.background = resources.getDrawable(R.drawable.wrong_back)
                userWAnswer++
                binding.wrongAnswer.text = userWAnswer.toString()
                findAnswer()
            }
            disableClickableOfOption()
        }
        binding.Option3.setOnClickListener {
            pauseTimer()
            userAnswer = "c"
            if (answer == userAnswer) {
                binding.Option3.background = resources.getDrawable(R.drawable.right_back)
                userCAnswer++
                binding.correctAnswer.text = userCAnswer.toString()
            } else {
                binding.Option3.background = resources.getDrawable(R.drawable.wrong_back)
                userWAnswer++
                binding.wrongAnswer.text = userWAnswer.toString()
                findAnswer()
            }
            disableClickableOfOption()
        }
        binding.Option4.setOnClickListener {
            pauseTimer()
            userAnswer = "d"
            if (answer == userAnswer) {
                binding.Option4.background = resources.getDrawable(R.drawable.right_back)
                userCAnswer++
                binding.correctAnswer.text = userCAnswer.toString()
            } else {
                binding.Option4.background = resources.getDrawable(R.drawable.wrong_back)
                userWAnswer++
                binding.wrongAnswer.text = userWAnswer.toString()
                findAnswer()
            }
            disableClickableOfOption()
        }
        val appOpenAdsManager = AppOpenAdsManager.getInstance(this)
        appOpenAdsManager.fetchAd()
    }
    private fun startQuiz() {
        parents = intent.getStringExtra("parent") ?: ""
        children = intent.getStringExtra("children") ?: ""
        val databaseRef = FirebaseDatabase.getInstance().reference.child(parents).child(children)
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                questionCount = snapshot.childrenCount.toInt()
                do {
                    val number = Random.nextInt(1, questionCount)
                    questionSet.add(number)
                } while (questionSet.size < noOFQuestion)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        binding.progressBar.visibility=View.VISIBLE
        gameLogic()
    }
    private fun gameLogic() {
        restoreOption()
        val databaseReference = database.reference.child(parents).child(children)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (questionNo < questionSet.size) {
                    question = snapshot.child(questionSet.elementAt(questionNo).toString())
                        .child("Question").value.toString()
                    option1 = snapshot.child(questionSet.elementAt(questionNo).toString())
                        .child("a").value.toString()
                    option2 = snapshot.child(questionSet.elementAt(questionNo).toString())
                        .child("b").value.toString()
                    option3 = snapshot.child(questionSet.elementAt(questionNo).toString())
                        .child("c").value.toString()
                    option4 = snapshot.child(questionSet.elementAt(questionNo).toString())
                        .child("d").value.toString()
                    answer = snapshot.child(questionSet.elementAt(questionNo).toString())
                        .child("Answer").value.toString()
                    binding.Question.text = question
                    binding.Option1.text = option1
                    binding.Option2.text = option2
                    binding.Option3.text = option3
                    binding.Option4.text = option4
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.linearLayoutInfo.visibility = View.VISIBLE
                    binding.scrollView2.visibility = View.VISIBLE
                    binding.linearLayoutBtn.visibility = View.VISIBLE
                    startTimer()
                } else {
                    val dialogMessage = AlertDialog.Builder(this@QuizActivity)
                    dialogMessage.setTitle(R.string.app_name)
                    dialogMessage.setMessage("Congratulation !!! \n You have answered all question of this set \n Do you want to show the result? ")
                    dialogMessage.setCancelable(false)
                    dialogMessage.setPositiveButton("See Result") { dialogWindow, position ->
                        sendScore()
                    }
                    dialogMessage.setNegativeButton("Play Again") { dialogWindow, position ->
                        startActivity(Intent(this@QuizActivity, MainActivity::class.java))
                        finish()
                    }
                    dialogMessage.create().show()
                }
                questionNo++
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun findAnswer() {
        when (answer) {
            //  "a" -> binding.Option1.setBackgroundColor(Color.GREEN)
            "a" -> binding.Option1.background = resources.getDrawable(R.drawable.right_back)
            "b" -> binding.Option2.background = resources.getDrawable(R.drawable.right_back)
            "c" -> binding.Option3.background = resources.getDrawable(R.drawable.right_back)
            "d" -> binding.Option4.background = resources.getDrawable(R.drawable.right_back)
        }
    }
    private fun disableClickableOfOption() {
        binding.Option1.isClickable = false
        binding.Option2.isClickable = false
        binding.Option3.isClickable = false
        binding.Option4.isClickable = false
    }
    private fun restoreOption() {
        // binding.Option1.setBackgroundColor(Color.WHITE)
        binding.Option1.background = resources.getDrawable(R.drawable.option_background)
        binding.Option2.background = resources.getDrawable(R.drawable.option_background)
        binding.Option3.background = resources.getDrawable(R.drawable.option_background)
        binding.Option4.background = resources.getDrawable(R.drawable.option_background)

        binding.Option1.isClickable = true
        binding.Option2.isClickable = true
        binding.Option3.isClickable = true
        binding.Option4.isClickable = true
    }
    private fun startTimer() {
        timer = object : CountDownTimer(leftTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                leftTime = millisUntilFinished
                updateCountDownText()
            }
            override fun onFinish() {
                disableClickableOfOption()
                resetTimer()
                updateCountDownText()
                binding.Question.text =
                    "Sorry , Time is Over !!! \n Please Continue with next Question"
                timerContinue = false
            }
        }.start()
        timerContinue = true
    }
    private fun updateCountDownText() {
        val remainingTime: Int = (leftTime / 1000).toInt()
        binding.time.text = remainingTime.toString()
    }
    private fun pauseTimer() {
        timer.cancel()
        timerContinue = false
    }
    private fun resetTimer() {
        pauseTimer()
        leftTime = totalTime
        updateCountDownText()
    }
    fun sendScore() {
        user?.let {
            val userId = it.uid
            scoreRef.child("Scores").child(userId).child("Correct Answer").setValue(userCAnswer)
            scoreRef.child("Scores").child(userId).child("Wrong Answer").setValue(userWAnswer)
                .addOnSuccessListener {
                    Toast.makeText(
                        applicationContext,
                        "Score Updated Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@QuizActivity, ResultActivity::class.java)
                    startActivity(intent)
                    finish()
                }
        }
    }
    override fun onResume() {
        super.onResume()
        val appOpenAdsManager = AppOpenAdsManager.getInstance(this)
        appOpenAdsManager.onResume(this)
    }
}