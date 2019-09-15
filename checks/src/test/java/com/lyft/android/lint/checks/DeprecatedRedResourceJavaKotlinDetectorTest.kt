package com.lyft.android.lint.checks

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests for the [DeprecatedRedResourceJavaKotlinDetector] custom lint check.
 */
@RunWith(JUnit4::class)
class DeprecatedRedResourceJavaKotlinDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> =
        mutableListOf(DeprecatedRedResourceJavaKotlinDetector.ISSUE)

    override fun getDetector(): Detector = DeprecatedRedResourceJavaKotlinDetector()

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
        int redResId = R.color.red;
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
        int deprecatedRedResId = R.color.deprecated_red;
    }
}
        """
                )
            )
            .run()
            .expect(
                """
src/com/lyft/android/lint/checks/TestClass.java:7: Error: The R.color.deprecated_red resource is deprecated and should not be used. [DeprecatedRedResourceJavaKotlin]
        int deprecatedRedResId = R.color.deprecated_red;
                                 ~~~~~~~~~~~~~~~~~~~~~~
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
import com.lyft.android.ui.PrettyButton

fun test(context: Context) {
    val redResId = R.color.red
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
    val deprecatedRedResId = R.color.deprecated_red
}
        """
                )
            )
            .run()
            .expect(
                """
src/com/lyft/android/lint/checks/test.kt:5: Error: The R.color.deprecated_red resource is deprecated and should not be used. [DeprecatedRedResourceJavaKotlin]
    val deprecatedRedResId = R.color.deprecated_red
                             ~~~~~~~~~~~~~~~~~~~~~~
1 errors, 0 warnings
            """
            )
    }
}

// Including these stub resources is necessary in order to get the tests to run successfully.


private val COLOR_RESOURCES = LintDetectorTest.xml(
    "res/values/colors.xml", """
<resources>
    <color name="red">#ff0000</color>
    <color name="deprecated_red">#ff0000</color>
</resources>
"""
)
