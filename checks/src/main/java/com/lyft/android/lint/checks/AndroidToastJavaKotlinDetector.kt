package com.lyft.android.lint.checks

import com.android.tools.lint.detector.api.*

/**
 * A custom lint check that prohibits use of the `android.widget.Toast` class.
 */
class AndroidToastJavaKotlinDetector : Detector(), SourceCodeScanner {

    companion object {
        val ISSUE = Issue.create(
            id = "AndroidToastJavaKotlin",
            briefDescription = "Don't use `android.widget.Toast`",
            explanation = "The `android.widget.Toast` class should not be used",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                AndroidToastJavaKotlinDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }
}























