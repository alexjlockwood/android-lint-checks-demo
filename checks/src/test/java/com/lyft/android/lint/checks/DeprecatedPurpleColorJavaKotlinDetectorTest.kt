package com.lyft.android.lint.checks

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests for the [DeprecatedPurpleColorJavaKotlinDetector] custom lint check.
 */
@RunWith(JUnit4::class)
class DeprecatedPurpleColorJavaKotlinDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> =
        mutableListOf(DeprecatedPurpleColorJavaKotlinDetector.ISSUE)

    override fun getDetector(): Detector = DeprecatedPurpleColorJavaKotlinDetector()

    @Test
    fun testJava_expectPass() {
        lint()
            .files(
                COLOR_RESOURCES,
                java(
                    """
package com.lyft.android.lint.checks;

public class TestClass {

    public static void test() {
        int purpleResId = R.color.purple;
    }
}
        """
                )
            )
            .run()
            .expectClean()
    }

    @Test
    fun testJava_expectFail() {
        lint()
            .files(
                COLOR_RESOURCES,
                java(
                    """
package com.lyft.android.lint.checks;

public class TestClass {

    public static void test() {
        int deprecatedPurpleResId = R.color.deprecated_purple;
    }
}
        """
                )
            )
            .run()
            .expect(
                """
src/com/lyft/android/lint/checks/TestClass.java:7: Error: R.color.deprecated_purple should not be used. [DeprecatedPurpleColorJavaKotlin]
        int deprecatedPurpleResId = R.color.deprecated_purple;
                                    ~~~~~~~~~~~~~~~~~~~~~~~~~
1 errors, 0 warnings
            """
            )
    }

    @Test
    fun testKotlin_expectPass() {
        lint()
            .files(
                COLOR_RESOURCES,
                kotlin(
                    """
package com.lyft.android.lint.checks

import android.content.Context

fun test(context: Context) {
    val purpleResId = R.color.purple
}
        """
                )
            )
            .run()
            .expectClean()
    }

    @Test
    fun testKotlin_expectFail() {
        lint()
            .files(
                COLOR_RESOURCES,
                kotlin(
                    """
package com.lyft.android.lint.checks

fun test() {
    val deprecatedPurpleResId = R.color.deprecated_purple
}
        """
                )
            )
            .run()
            .expect(
                """
src/com/lyft/android/lint/checks/test.kt:5: Error: R.color.deprecated_purple should not be used. [DeprecatedPurpleColorJavaKotlin]
    val deprecatedPurpleResId = R.color.deprecated_purple
                                ~~~~~~~~~~~~~~~~~~~~~~~~~
1 errors, 0 warnings
            """
            )
    }
}

// Including these stub resources is necessary in order to get the tests to run successfully.


private val COLOR_RESOURCES = LintDetectorTest.xml(
    "res/values/colors.xml", """
<resources>
    <color name="purple">#A828A8</color>
    <color name="deprecated_purple">#A828A8</color>
</resources>
"""
)
