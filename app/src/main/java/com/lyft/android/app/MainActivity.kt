package com.lyft.android.app

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.lyft.android.ui.DeprecatedButton

@Suppress("unused")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun showToast() {
        // Usages of the Android Toast class is prohibited by the
        // `AndroidToastJavaKotlinDetector` custom lint check.
        Toast.makeText(this, "Some random text", Toast.LENGTH_SHORT).show()
    }

    private fun createButton(): Button {
        // Creating new instances of DeprecatedButton is prohibited by the
        // `DeprecatedButtonJavaKotlinDetector` custom lint check.
        return DeprecatedButton(this)
    }

    private fun getRedColor(): Int {
        // References to the `R.color.deprecated_red` resource are prohibited by the
        // `DeprecatedRedResourceJavaKotlin` custom lint check.
        return ContextCompat.getColor(this, R.color.deprecated_red)
    }
}