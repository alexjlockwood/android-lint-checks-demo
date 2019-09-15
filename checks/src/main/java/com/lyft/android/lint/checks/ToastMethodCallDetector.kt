package com.lyft.android.lint.checks

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

/**
 * A custom lint check that prohibits usage of the `android.widget.Toast` class.
 */
class ToastMethodCallDetector : Detector(), Detector.UastScanner {

    companion object {
        val ISSUE = Issue.create(
            id = "ToastMethodCall",
            briefDescription = "Prohibits usages of `android.widget.Toast`",
            explanation = "Usages of `android.widget.Toast` are prohibited.",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                ToastMethodCallDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }

    override fun getApplicableMethodNames(): List<String> {
        // Return the list of method calls we want to analyze. The `visitMethod`
        // method below will be called each time lint sees the method being used
        // in Java and Kotlin code.
        return listOf("makeText")
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        if (!context.evaluator.isMemberInClass(method, "android.widget.Toast")) {
            // Ignore the method call if the method doesn't belong to
            // the `android.widget.Toast` class.
            return
        }
        context.report(
            issue = ISSUE,
            scope = node,
            location = context.getLocation(node),
            message = "Use a `Snackbar` from the support library instead."
        )
    }
}
