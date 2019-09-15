package com.lyft.android.lint.checks

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests for the [AndroidToastJavaKotlinDetector] custom lint check.
 */
@RunWith(JUnit4::class)
class AndroidToastJavaKotlinDetectorTest : LintDetectorTest() {

    override fun getIssues() = mutableListOf(AndroidToastJavaKotlinDetector.ISSUE)

    override fun getDetector(): Detector = AndroidToastJavaKotlinDetector()

    @Test
    fun testJava_expectPass() {
        lint()
            .files(
                java(
                    """
package com.lyft.android.lint.checks;

public class TestClass {

    public void makeText() {}
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
                java(
                    """
package com.lyft.android.lint.checks;

import android.content.Context;
import android.widget.Toast;

public class TestClass {

    public void makeText(Context context) {
        Toast.makeText(context, "Some text", Toast.LENGTH_SHORT).show();
    }
}
                """
                )
            )
            .run()
            .expect(
                """
src/com/lyft/android/lint/checks/TestClass.java:10: Error: Use a Snackbar from the support library instead. [AndroidToastJavaKotlin]
        Toast.makeText(context, "Some text", Toast.LENGTH_SHORT).show();
        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
1 errors, 0 warnings
                """
            )
    }

    @Test
    fun testKotlin_expectPass() {
        lint()
            .files(
                kotlin(
                    """
package com.lyft.android.lint.checks

class TestClass {

    fun makeText() {}
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
                kotlin(
                    """
package com.lyft.android.lint.checks

import android.content.Context
import android.widget.Toast

class TestClass {

    fun makeText(context: Context) {
        Toast.makeText(context, "Some text", Toast.LENGTH_SHORT).show()
    }
}
                """
                )
            )
            .run()
            .expect(
                """
src/com/lyft/android/lint/checks/TestClass.kt:10: Error: Use a Snackbar from the support library instead. [AndroidToastJavaKotlin]
        Toast.makeText(context, "Some text", Toast.LENGTH_SHORT).show()
        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
1 errors, 0 warnings
                """
            )
    }
}
