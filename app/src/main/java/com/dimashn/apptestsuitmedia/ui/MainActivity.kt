package com.dimashn.apptestsuitmedia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.dimashn.apptestsuitmedia.R
import com.dimashn.apptestsuitmedia.customview.MyButton
import com.dimashn.apptestsuitmedia.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var buttonCheck: MyButton
    private lateinit var buttonNext: MyButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        buttonCheck = binding.checkButton
        buttonNext = binding.nextButton
        setMyButtonEnable()

        binding.nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        binding.nameEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.nameEditText.hint = ""
            } else {
                if (binding.nameEditText.text.isNullOrEmpty()) {
                    binding.nameEditText.hint = "Name"
                }
            }
        }

        if (binding.nameEditText.text.isNullOrEmpty()) {
            binding.nameEditText.hint = "Name"
        }


        binding.palindromeCheckEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        binding.palindromeCheckEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.palindromeCheckEditText.hint = ""
            } else {
                if (binding.palindromeCheckEditText.text.isNullOrEmpty()) {
                    binding.palindromeCheckEditText.hint = "Palindrome"
                }
            }
        }

        if (binding.palindromeCheckEditText.text.isNullOrEmpty()) {
            binding.palindromeCheckEditText.hint = "Palindrome"
        }

        setupAction()

    }

    private fun setupAction() {
        buttonCheck.setOnClickListener {
            if (valid()) {
                val palindrome = binding.palindromeCheckEditText.text.toString()

                val message = if (isPalindrome(palindrome)) {
                    resources.getString(R.string.is_palindrome)
                } else {
                    resources.getString(R.string.not_palindrome)
                }

                showSnackbar(message)

            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.check_input),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        buttonNext.setOnClickListener{
            val name = binding.nameEditText.text.toString()
            val intent = Intent(this, SecondScreen::class.java)
            intent.putExtra("EXTRA_NAME", name)
            startActivity(intent)
        }
    }

    private fun valid(): Boolean {
        return !binding.palindromeCheckEditText.text.isNullOrEmpty()
    }

    private fun setMyButtonEnable() {
        buttonCheck.isEnabled = valid()
        buttonNext.isEnabled = !binding.nameEditText.text.isNullOrEmpty()
    }

    private fun isPalindrome(str: String): Boolean {
        val cleanStr = str.lowercase(Locale.ROOT).replace("\\W".toRegex(), "")
        return cleanStr == cleanStr.reversed()
    }

    private fun showSnackbar(message: String) {
        val palindrome = binding.palindromeCheckEditText.text.toString()
        val backgroundColor = if (isPalindrome(palindrome)) {
            resources.getColor(R.color.blue_700)
        } else {
            resources.getColor(R.color.red)
        }
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setTextColor(resources.getColor(R.color.white))
            .setBackgroundTint(backgroundColor)
            .show()
    }
}