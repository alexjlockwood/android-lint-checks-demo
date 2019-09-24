package com.lyft.android.app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

@Suppress("unused")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun showToast() {
        // LINT ERROR: the `android.widget.Toast` class should not be used
        Toast.makeText(this, "Some text", Toast.LENGTH_SHORT).show()
    }
}