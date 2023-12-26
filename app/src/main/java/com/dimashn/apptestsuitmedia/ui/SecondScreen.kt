package com.dimashn.apptestsuitmedia.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dimashn.apptestsuitmedia.databinding.SecondScreenBinding

class SecondScreen : AppCompatActivity() {

    private lateinit var binding : SecondScreenBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding= SecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Second Screen"
            setDisplayHomeAsUpEnabled(true)
        }

        val name = intent.getStringExtra("EXTRA_NAME")
        binding.tvName.text = name

        val firstName = intent.getStringExtra("FIRST_NAME")
        val lastName = intent.getStringExtra("LAST_NAME")
        if (firstName != null && lastName != null) {
            val fullName = "$firstName $lastName"
            binding.tvSelectedName.text = fullName
        } else {
            binding.tvSelectedName.visibility = View.GONE
        }

        setupAction()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupAction() {
        binding.choseUserButton.setOnClickListener{
            startActivity(Intent(this, ThirdScreen::class.java))
        }
    }
}