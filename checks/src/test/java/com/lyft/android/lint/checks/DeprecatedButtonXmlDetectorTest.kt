package com.lyft.android.lint.checks

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests for the [DeprecatedButtonXmlDetector] custom lint check.
 */
@RunWith(JUnit4::class)
class DeprecatedButtonXmlDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> = mutableListOf(DeprecatedButtonXmlDetector.ISSUE)

    override fun getDetector(): Detector = DeprecatedButtonXmlDetector()

    @Test
    fun expectPass() {
        lint()
            .files(
                xml(
                    "res/layout/layout.xml", """
<com.google.android.material.button.MaterialButton xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
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
<com.lyft.android.ui.DeprecatedButton xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    />
"""
                )
            ).run()
            .expect(
                """
res/layout/layout.xml:2: Error: DeprecatedButton should not be used. [DeprecatedButtonXml]
<com.lyft.android.ui.DeprecatedButton xmlns:android="http://schemas.android.com/apk/res/android"
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
1 errors, 0 warnings
            """
            )
    }
}