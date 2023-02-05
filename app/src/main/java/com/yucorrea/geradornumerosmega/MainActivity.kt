package com.yucorrea.geradornumerosmega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)

        val result = prefs.getString("result", null)

        val editText : EditText = findViewById(R.id.editText)
        val txtResult : TextView = findViewById(R.id.result)
        val btnGenerate : Button = findViewById(R.id.btn_generate)

        result?.let {
            txtResult.text = "Última aposta: ${it}"
        }

        btnGenerate.setOnClickListener {
            val text = editText.text.toString()

            generateNumbers(text, txtResult);
            editText.setText("")
        }

    }

    private fun generateNumbers(text: String, txtResult: TextView) {

        if(text.isEmpty()) {
            Toast.makeText(this, "Escolha um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val qtd = text.toInt()

        if(qtd < 6 || qtd > 15) {
            Toast.makeText(this, "Escolha um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val numbers = mutableSetOf<Int>()

        while (true) {
            val random = Random.nextInt(60)
            numbers.add(random)

            if(numbers.size >= qtd) {
                break
            }
        }

        txtResult.text = numbers.joinToString("-")
        prefs.edit().apply{
            putString("result", txtResult.text.toString())
            apply()
        }

    }

}