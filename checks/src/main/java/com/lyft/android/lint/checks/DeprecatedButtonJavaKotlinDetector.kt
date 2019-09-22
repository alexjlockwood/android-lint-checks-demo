package com.lyft.android.lint.checks

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

/**
 * A custom lint check that prohibits new instances of `DeprecatedButton` from being instantiated
 * in Java and Kotlin code.
 */
class DeprecatedButtonJavaKotlinDetector : Detector(), SourceCodeScanner {

    companion object {
        val ISSUE = Issue.create(
            id = "DeprecatedButtonJavaKotlin",
            briefDescription = "Prohibits DeprecatedButton instances from being instantiated",
            explanation = "DeprecatedButton is deprecated and should no longer be used",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                DeprecatedButtonJavaKotlinDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }

    override fun getApplicableConstructorTypes(): List<String> {
        // Return the list of constructor calls we want to analyze. The `visitConstructor` method
        // below will be called each time lint sees an instance of this class being instantiated in
        // Java and Kotlin code.
        return listOf("com.lyft.android.ui.DeprecatedButton")
    }

    override fun visitConstructor(
        context: JavaContext,
        node: UCallExpression,
        constructor: PsiMethod
    ) {
        context.report(
            issue = ISSUE,
            scope = node,
            location = context.getLocation(node),
            message = "Use a `PrettyButton` instead."
        )
    }
}