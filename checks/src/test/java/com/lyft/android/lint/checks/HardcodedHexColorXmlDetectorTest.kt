package com.lyft.android.lint.checks

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import com.lyft.android.lint.solutions.HardcodedHexColorXmlDetector
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests for the [HardcodedHexColorXmlDetector] custom lint check.
 */
@RunWith(JUnit4::class)
class HardcodedHexColorXmlDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> = mutableListOf(HardcodedHexColorXmlDetector.ISSUE)

    override fun getDetector(): Detector =
        HardcodedHexColorXmlDetector()

    @Test
    fun expectPass() {
        lint()
            .files(
                xml(
                    "res/layout/layout.xml", """
<View xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:background="@color/black"
    />
"""
                )
            ).run()
            .expectClean()
    }

    @Test
    fun expectFail() {
        lint()
            .files(
                xml(
                    "res/layout/layout.xml", """
<View xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:background="#000"
    />
"""
                )
            ).run()
            .expect(
                """
res/layout/layout.xml:5: Error: Hardcoded hex colors should be declared in a <color> resource. [HardcodedHexColorXml]
    android:background="#000"
                        ~~~~
1 errors, 0 warnings
            """
            )
    }
}