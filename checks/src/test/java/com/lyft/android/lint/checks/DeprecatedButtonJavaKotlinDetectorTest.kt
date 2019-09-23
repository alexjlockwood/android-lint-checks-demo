package com.lyft.android.lint.checks

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests for the [DeprecatedButtonJavaKotlinDetector] custom lint check.
 */
@RunWith(JUnit4::class)
class DeprecatedButtonJavaKotlinDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> =
        mutableListOf(DeprecatedButtonJavaKotlinDetector.ISSUE)

    override fun getDetector(): Detector = DeprecatedButtonJavaKotlinDetector()

    @Test
    fun testJava_expectPass() {
        lint()
            .files(
                STUB_DEPRECATED_BUTTON,
                STUB_MATERIAL_BUTTON,
                java(
                    """
package com.lyft.android.lint.checks;

import android.content.Context;
import com.google.android.material.button.MaterialButton;

public class TestClass {
    
    public static void test(Context context) {
        MaterialButton materialButton = new MaterialButton(context);
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
                STUB_MATERIAL_BUTTON,
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
src/com/lyft/android/lint/checks/TestClass.java:10: Error: DeprecatedButton should not be used. [DeprecatedButtonJavaKotlin]
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
                STUB_MATERIAL_BUTTON,
                kotlin(
                    """
package com.lyft.android.lint.checks

import android.content.Context
import com.google.android.material.button.MaterialButton

fun test(context: Context) {
    val materialButton = MaterialButton(context)
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
                STUB_MATERIAL_BUTTON,
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
src/com/lyft/android/lint/checks/test.kt:8: Error: DeprecatedButton should not be used. [DeprecatedButtonJavaKotlin]
    val deprecatedButton = DeprecatedButton(context)
                           ~~~~~~~~~~~~~~~~~~~~~~~~~
1 errors, 0 warnings
            """
            )
    }
}

// Including these stub classes for non-framework widgets is necessary
// in order to get the tests to run successfully.

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

private val STUB_MATERIAL_BUTTON = LintDetectorTest.kotlin(
    """
package com.google.android.material.button

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

open class MaterialButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatButton(context, attrs)
            """
)