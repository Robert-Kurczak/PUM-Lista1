package pl.edu.uwr.pum

import android.content.Intent
import android.content.Intent.CATEGORY_BROWSABLE
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), OnClickListener{
    companion object{
        const val secondActivityTextName: String = "pl.edu.uwr.pum.secondActivityText"
    }

    private fun secondActivityMessage(data: String){
        val secondActivityIntent = Intent(this, ResultActivity::class.java)
        secondActivityIntent.putExtra(secondActivityTextName, data)
        startActivity(secondActivityIntent)
    }

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var cheatButton: Button
    private lateinit var checkOnlineButton: Button

    private lateinit var questionText: TextView

    private var currentQuestion: Int = 0
    private var score: Int = 0
    private var correct: Int = 0
    private var cheats: Int = 0

    private val questions = arrayOf(
        Pair("Sound waves can generate heat.", true),
        Pair("Fire can have a shadow.", true),
        Pair("It is possible to know a particle's exact location.", false),
        Pair("Sound waves are invisible.", false),
        Pair("One bit of light can bounce off another bit of light.", true),
        Pair("Traveling at high speed does affect your mass.", false),
        Pair("An electron is a quantum object.", true),
        Pair("Time goes faster at the top of a building compared to the bottom.", true),
        Pair("Gold can be created from other elements.", true),
        Pair("Electron have the shape of a ball.", false)
    )

    private fun nextQuestion(){
        //Display result
        if(currentQuestion == 9){
            questionText.text = "Score: $score Correct: $correct Cheats: $cheats"

            trueButton.visibility = View.GONE
            falseButton.visibility = View.GONE
            cheatButton.visibility = View.GONE
            checkOnlineButton.visibility = View.GONE
        }
        else{
            currentQuestion++
            questionText.text = questions[currentQuestion].first
        }
    }

    private fun checkResult(answer: Boolean, cheated: Boolean = false){
        if(cheated){
            cheats++
            score -= 15
            secondActivityMessage(questions[currentQuestion].second.toString())
        }
        else if(answer == questions[currentQuestion].second){
            score += 10
            correct++
            nextQuestion()
        }
        else{
            score -= 10
            nextQuestion()
        }
    }

    private fun checkOnline(URL: String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URL)).apply{
            addCategory(CATEGORY_BROWSABLE)
        }

        if (intent.resolveActivity(packageManager) != null)
            startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState != null){
            currentQuestion = savedInstanceState.getInt("questionNumber")
            score = savedInstanceState.getInt("score")
            correct = savedInstanceState.getInt("correct")
            cheats = savedInstanceState.getInt("cheats")
        }

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        cheatButton = findViewById(R.id.cheat_button)
        checkOnlineButton = findViewById(R.id.check_online_button)

        questionText = findViewById(R.id.question_text)
        questionText.text = questions[currentQuestion].first

        trueButton.setOnClickListener(this)
        falseButton.setOnClickListener(this)
        cheatButton.setOnClickListener(this)
        checkOnlineButton.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.true_button -> checkResult(true)
            R.id.false_button -> checkResult(false)
            R.id.check_online_button ->
                checkOnline("https://www.google.com/search?q=${questions[currentQuestion].first}")
            else -> checkResult(answer = false, cheated = true)
        }
    }

    override fun onSaveInstanceState(outState: Bundle){
        super.onSaveInstanceState(outState)

        outState.putInt("questionNumber", currentQuestion)
        outState.putInt("score", score)
        outState.putInt("correct", correct)
        outState.putInt("cheats", cheats)
    }
}