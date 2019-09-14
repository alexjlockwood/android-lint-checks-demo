package com.lyft.android.lint.checks

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DeprecatedCustomViewDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> = mutableListOf(DeprecatedCustomViewDetector.ISSUE)

    override fun getDetector(): Detector = DeprecatedCustomViewDetector()

    @Test
    fun expectPass() {
        lint()
            .files(
                xml(
                    "res/layout/layout.xml", """
<View xmlns:android="http://schemas.android.com/apk/res/android"
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
<com.lyft.android.lint.app.DeprecatedCustomView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    />
"""
                )
            ).run()
            .expect(
                """
res/layout/layout.xml:2: Error: Don't use DeprecatedCustomView! [DeprecatedCustomView]
<com.lyft.android.lint.app.DeprecatedCustomView xmlns:android="http://schemas.android.com/apk/res/android"
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
1 errors, 0 warnings
            """
            )
    }
}