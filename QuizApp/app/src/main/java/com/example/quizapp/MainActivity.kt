package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.submitUsername.setOnClickListener{
            if(binding.inputUsername.text.toString() == ""){
                Toast.makeText(this, "لطفا نام کاربری را وارد نمایید", Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("username", binding.inputUsername.text.toString())
                startActivity(intent)
            }
        }
    }
}