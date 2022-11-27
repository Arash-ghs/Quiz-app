package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.databinding.ActivityQuestionBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class QuestionActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuestionBinding
    var currentQuestion = 0
    var selectedOption = 0
    var correctAnswer = 0
    var wrongAnswer = 0
    var username: String? = null
    lateinit var questionList:ArrayList<Question>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        questionList = Constants.getQuestions()
        loadQuestionData()

        username = getIntent().getStringExtra("username")
        binding.textViewQUsername.text = username

    }
    fun loadQuestionData(){
        val question = questionList[currentQuestion]
        binding.textViewQuestionTitle.text = question.question
        binding.imageViewFlag.setImageResource(question.image)
        binding.textViewOption1.text = question.optionOne
        binding.textViewOption2.text = question.optionTwo
        binding.textViewOption3.text = question.optionThree
        binding.textViewOption4.text = question.optionFour
        binding.progressBar.progress = currentQuestion + 1
        binding.textViewProcess.text = "${currentQuestion+1} / ${binding.progressBar.max}"
        setDefault()
    }

    fun setDefault(){
        val textViewOptions = ArrayList<TextView>()
        textViewOptions.add(binding.textViewOption1)
        textViewOptions.add(binding.textViewOption2)
        textViewOptions.add(binding.textViewOption3)
        textViewOptions.add(binding.textViewOption4)
        for(item in textViewOptions){
            item.typeface = Typeface.DEFAULT
            item.background = ContextCompat.getDrawable(this, R.drawable.options_background)
            item.setTextColor(Color.parseColor("#7a8089"))
        }
        setUsernameColor()
    }

    fun onOptionsClicked(view:View){
        setDefault()
        val selected = view as TextView

        when(selected.tag.toString()){
            "option1"->{
                selectedOption = 1
            }
            "option2"->{
                selectedOption = 2
            }
            "option3"->{
                selectedOption = 3
            }
            "option4"->{
                selectedOption = 4
            }
        }

        selected.typeface = Typeface.DEFAULT_BOLD
        selected.background = ContextCompat.getDrawable(this, R.drawable.options_background_selected)
        selected.setTextColor(Color.parseColor("#000000"))
    }

    fun submit(view:View) {
        if (currentQuestion == questionList.size - 1) {
            checkAnswers()
            showResult()
        }else {
            checkAnswers()
            selectedOption = 0
            currentQuestion++
            val handler = Handler()
            handler.postDelayed({ loadQuestionData() }, 1000)
            if (currentQuestion == questionList.size - 1) {
                binding.button.text = "دیدن نتیجه"
            }
        }
    }

    fun setOptionBackground(optionIndex:Int, drawableIndex:Int){
        when(optionIndex){
            1->{
                binding.textViewOption1.background = ContextCompat.getDrawable(this, drawableIndex)
            }
            2->{
                binding.textViewOption2.background = ContextCompat.getDrawable(this, drawableIndex)
            }
            3->{
                binding.textViewOption3.background = ContextCompat.getDrawable(this, drawableIndex)
            }
            4->{
                binding.textViewOption4.background = ContextCompat.getDrawable(this, drawableIndex)
            }
        }
    }

    fun checkAnswers(){
        if(questionList[currentQuestion].correctAnswer == selectedOption){
            setOptionBackground(selectedOption, R.drawable.options_background_correct)
            correctAnswer++
        }else{
            setOptionBackground(selectedOption, R.drawable.options_background_wrong)
            setOptionBackground(questionList[currentQuestion].correctAnswer, R.drawable.options_background_correct)
            wrongAnswer++
        }
    }

    fun setUsernameColor(){
        if(correctAnswer > wrongAnswer){
            binding.textViewQUsername.setTextColor(Color.GREEN)
        }
        else if(wrongAnswer > correctAnswer){
            binding.textViewQUsername.setTextColor(Color.RED)
        }else{
            binding.textViewQUsername.setTextColor(Color.BLACK)
        }
    }

    fun showResult() {
        val intent = Intent(this@QuestionActivity, result::class.java)
        intent.putExtra("correctAnswer", correctAnswer)
        intent.putExtra("wrongAnswer", wrongAnswer)
        intent.putExtra("username", username)
        startActivity(intent)
    }
}