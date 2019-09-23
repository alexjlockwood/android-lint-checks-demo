package com.lyft.android.lint.checks

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests for the [DeprecatedPurpleColorXmlDetector] custom lint check.
 */
@RunWith(JUnit4::class)
class DeprecatedPurpleColorXmlDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> = mutableListOf(DeprecatedPurpleColorXmlDetector.ISSUE)

    override fun getDetector(): Detector = DeprecatedPurpleColorXmlDetector()

    @Test
    fun expectPass() {
        lint()
            .files(
                xml(
                    "res/layout/layout.xml", """
<View xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:background="#000000"
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
    android:background="@color/deprecated_purple"
    />
"""
                )
            ).run()
            .expect(
                """
res/layout/layout.xml:5: Error: The @color/deprecated_purple resource is deprecated and should not be used. [DeprecatedPurpleColorXml]
    android:background="@color/deprecated_purple"
                        ~~~~~~~~~~~~~~~~~~~~~~~~
1 errors, 0 warnings
            """
            )
    }
}