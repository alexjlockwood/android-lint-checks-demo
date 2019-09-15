package com.lyft.android.lint.checks

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests for the [DeprecatedButtonConstructorDetector] custom lint check.
 */
@RunWith(JUnit4::class)
class DeprecatedButtonConstructorDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> =
        mutableListOf(DeprecatedButtonConstructorDetector.ISSUE)

    override fun getDetector(): Detector = DeprecatedButtonConstructorDetector()

    @Test
    fun testJava_expectPass() {
        lint()
            .files(
                STUB_DEPRECATED_BUTTON,
                STUB_PRETTY_BUTTON,
                java(
                    """
package com.lyft.android.lint.checks;

import android.content.Context;
import com.lyft.android.ui.PrettyButton;

public class TestClass {
    
    public static void test(Context context) {
        PrettyButton prettyButton = new PrettyButton(context);
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
                STUB_DEPRECATED_BUTTON,
                STUB_PRETTY_BUTTON,
                java(
                    """
package com.lyft.android.lint.checks;

import android.content.Context;
import com.lyft.android.ui.DeprecatedButton;

public class TestClass {
    
    public static void test(Context context) {
        DeprecatedButton deprecatedButton = new DeprecatedButton(context);
    }
}
        """
                )
            )
            .run()
            .expect(
                """
src/com/lyft/android/lint/checks/TestClass.java:10: Error: Use a PrettyButton instead. [DeprecatedButtonConstructor]
        DeprecatedButton deprecatedButton = new DeprecatedButton(context);
                                            ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
1 errors, 0 warnings
            """
            )
    }

    @Test
    fun testKotlin_expectPass() {
        lint()
            .files(
                STUB_DEPRECATED_BUTTON,
                STUB_PRETTY_BUTTON,
                kotlin(
                    """
package com.lyft.android.lint.checks

import android.content.Context
import com.lyft.android.ui.PrettyButton

fun test(context: Context) {
    val prettyButton = PrettyButton(context)
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
                STUB_DEPRECATED_BUTTON,
                STUB_PRETTY_BUTTON,
                kotlin(
                    """
package com.lyft.android.lint.checks

import android.content.Context
import com.lyft.android.ui.DeprecatedButton

fun test(context: Context) {
    val deprecatedButton = DeprecatedButton(context)
}
        """
                )
            )
            .run()
            .expect(
                """
src/com/lyft/android/lint/checks/test.kt:8: Error: Use a PrettyButton instead. [DeprecatedButtonConstructor]
    val deprecatedButton = DeprecatedButton(context)
                           ~~~~~~~~~~~~~~~~~~~~~~~~~
1 errors, 0 warnings
            """
            )
    }
}

// Including these stub classes are necessary in order to get the tests to run successfully.

private val STUB_DEPRECATED_BUTTON = LintDetectorTest.kotlin(
    """
package com.lyft.android.ui

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

open class DeprecatedButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatButton(context, attrs)
            """
)

private val STUB_PRETTY_BUTTON = LintDetectorTest.kotlin(
    """
package com.lyft.android.ui

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton

open class PrettyButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : MaterialButton(context, attrs)
            """
)