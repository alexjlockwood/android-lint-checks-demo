package com.lyft.android.app

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.lyft.android.ui.DeprecatedButton

@Suppress("unused")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun createButton(): Button {
        // Creating new instances of DeprecatedButton is prohibited by the
        // `DeprecatedButtonConstructorDetector` custom lint check.
        return DeprecatedButton(this)
    }
}