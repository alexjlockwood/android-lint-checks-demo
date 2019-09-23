package com.lyft.android.lint.checks

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests for the [OkayStringXmlDetector] custom lint check.
 */
@RunWith(JUnit4::class)
class OkayStringXmlDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> = mutableListOf(OkayStringXmlDetector.ISSUE)

    override fun getDetector(): Detector = OkayStringXmlDetector()

    @Test
    fun expectPass() {
        lint()
            .files(
                xml(
                    "res/values/strings.xml", """
<resources>
    <string name="ok">OK</string>
</resources>
                """
                )
            )
            .run()
            .expectClean()
    }

    @Test
    fun expectFail() {
        lint()
            .files(
                xml(
                    "res/values/strings.xml", """
<resources>
    <string name="ok">Okay</string>
</resources>
                """
                )
            )
            .run()
            .expect(
                """
res/values/strings.xml:3: Error: 'Okay' should be spelled 'OK' in string resources. [OkayStringXml]
    <string name="ok">Okay</string>
                      ~~~~~
1 errors, 0 warnings
            """
            )
    }
}