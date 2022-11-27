package com.example.quizapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.quizapp.databinding.ActivityResultBinding

class result : AppCompatActivity() {
    lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        val username = getIntent().getStringExtra("username")
        val correctAnswer = getIntent().getIntExtra("correctAnswer", 0)
        var wrongAnswer = getIntent().getIntExtra("wrongAnswer", 0)
        var wrongScore = 0

        binding.textViewCorrect.text = correctAnswer.toString()
        binding.textViewWrong.text = wrongAnswer.toString()
        binding.textViewUsername.text = username

        if (correctAnswer == 6) {
            binding.textViewCorrect.visibility = View.GONE
            binding.textViewWrong.visibility = View.GONE
            binding.netScore.visibility = View.GONE
            binding.textView5.visibility = View.GONE
            binding.textView6.visibility = View.GONE
            binding.textView8.visibility = View.GONE
            binding.textViewPercentage.visibility = View.GONE
        }

        if (correctAnswer > wrongAnswer) {
            binding.textViewUsername.setTextColor(Color.GREEN)
        } else if (wrongAnswer > correctAnswer) {
            binding.textViewUsername.setTextColor(Color.RED)
        } else {
            binding.textViewUsername.setTextColor(Color.BLACK)
        }

        var wrongTmp = wrongAnswer

        while (wrongTmp != 0) {
            if (wrongTmp - 3 >= 0) {
                wrongTmp -= 3
                wrongScore++
            }else{
                wrongTmp = 0
            }
        }

        binding.netScore.text = (correctAnswer - wrongScore).toString()

        binding.textViewPercentage.text = "نسبت پاسخ اشتباه به درست: ${((wrongAnswer / correctAnswer) * 100)}"
    }
}