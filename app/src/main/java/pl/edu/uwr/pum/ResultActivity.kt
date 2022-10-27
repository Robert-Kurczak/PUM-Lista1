package pl.edu.uwr.pum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val data = intent.getStringExtra(MainActivity.secondActivityTextName)

        resultText = findViewById(R.id.result_text)
        resultText.text = data
    }
}