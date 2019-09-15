package com.lyft.android.lint.checks

import com.android.resources.ResourceType
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UElement

/**
 * A custom lint check that prohibits usages of the `R.color.deprecated_red` resource in
 * Java and Kotlin code.
 */
class DeprecatedRedColorResourceReferenceDetector : Detector(), Detector.UastScanner {

    companion object {
        val ISSUE = Issue.create(
            id = "DeprecatedRedColorResourceReference",
            briefDescription = "Prohibits usages of the `deprecated_red` color resource in Java and Kotlin code",
            explanation = "The `deprecated_red` color resource is deprecated and should no longer be used",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                DeprecatedRedColorResourceReferenceDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }

    override fun appliesToResourceRefs(): Boolean {
        // Return true to ensure lint will analyze references to Android resources made
        // in Java and Kotlin code.
        return true
    }

    override fun visitResourceReference(
        context: JavaContext,
        node: UElement,
        type: ResourceType,
        name: String,
        isFramework: Boolean
    ) {
        if (type != ResourceType.COLOR) {
            // Ignore the resource reference if it isn't a color resource.
            return
        }
        if (isFramework) {
            // Ignore the resource reference if this is a color resource from the Android framework
            // (i.e. `android.R.color.***`).
            return
        }
        if (name != "deprecated_red") {
            // Finally, ignore the resource reference if it isn't named "deprecated_red".
            return
        }

        context.report(
            issue = ISSUE,
            scope = node,
            location = context.getLocation(node),
            message = "The `R.color.deprecated_red` resource is deprecated and should not be used."
        )
    }
}