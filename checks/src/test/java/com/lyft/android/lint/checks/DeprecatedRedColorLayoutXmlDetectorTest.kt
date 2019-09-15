package com.lyft.android.lint.checks

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests for the [DeprecatedRedColorLayoutXmlDetector] custom lint check.
 */
@RunWith(JUnit4::class)
class DeprecatedRedColorLayoutXmlDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> = mutableListOf(DeprecatedRedColorLayoutXmlDetector.ISSUE)

    override fun getDetector(): Detector = DeprecatedRedColorLayoutXmlDetector()

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
    android:background="@color/deprecated_red"
    />
"""
                )
            ).run()
            .expect(
                """
res/layout/layout.xml:5: Error: The @color/deprecated_red resource is deprecated and should not be used. [DeprecatedRedColorLayoutXml]
    android:background="@color/deprecated_red"
                        ~~~~~~~~~~~~~~~~~~~~~
1 errors, 0 warnings
            """
            )
    }
}