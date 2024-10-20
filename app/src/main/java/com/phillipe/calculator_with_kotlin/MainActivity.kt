package com.phillipe.calculator_with_kotlin

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var currentInput = ""
    private var operator = ""
    private var operand1: Double? = null
    private val decimalFormat = DecimalFormat("#.######")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.tvDisplay)

        // Number buttons
        val buttons = listOf(
            findViewById<Button>(R.id.btn0), findViewById<Button>(R.id.btn1),
            findViewById<Button>(R.id.btn2), findViewById<Button>(R.id.btn3),
            findViewById<Button>(R.id.btn4), findViewById<Button>(R.id.btn5),
            findViewById<Button>(R.id.btn6), findViewById<Button>(R.id.btn7),
            findViewById<Button>(R.id.btn8), findViewById<Button>(R.id.btn9)
        )

        for (button in buttons) {
            button.setOnClickListener { appendNumber((it as Button).text.toString()) }
        }

        // Operation buttons
        findViewById<Button>(R.id.btnAdd).setOnClickListener { setOperator("+") }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { setOperator("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { setOperator("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { setOperator("/") }

        // Clear and equals buttons
        findViewById<Button>(R.id.btnClear).setOnClickListener { clear() }
        findViewById<Button>(R.id.btnEqual).setOnClickListener { calculateResult() }
    }

    private fun appendNumber(number: String) {
        if (currentInput.isEmpty() && number == "0") return // Prevent leading zero
        currentInput += number
        display.text = currentInput
    }

    private fun setOperator(op: String) {
        if (currentInput.isEmpty()) return // Prevent consecutive operators
        operand1 = currentInput.toDoubleOrNull()
        operator = op
        currentInput = ""
    }

    private fun calculateResult() {
        val operand2 = currentInput.toDoubleOrNull()
        if (operand1 != null && operand2 != null) {
            val result = when (operator) {
                "+" -> operand1!! + operand2
                "-" -> operand1!! - operand2
                "*" -> operand1!! * operand2
                "/" -> {
                    if (operand2 == 0.0) {
                        display.text = "Erro"
                        return
                    }
                    operand1!! / operand2
                }
                else -> return
            }
            display.text = decimalFormat.format(result)
            currentInput = result.toString()
        }
    }

    private fun clear() {
        currentInput = ""
        operator = ""
        operand1 = null
        display.text = "0"
    }
}
