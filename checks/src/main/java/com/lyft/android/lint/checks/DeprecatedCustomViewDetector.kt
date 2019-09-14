package com.lyft.android.lint.checks

import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Element

class DeprecatedCustomViewDetector : ResourceXmlDetector() {

    companion object {
        val ISSUE = Issue.create(
            id = "DeprecatedCustomView",
            briefDescription = "Detects usages of the DeprecatedCustomView class",
            explanation = """
                    Detects usages of the DeprecatedCustomView class.
                    """,
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                DeprecatedCustomViewDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }

    override fun appliesTo(folderType: ResourceFolderType): Boolean {
        return folderType == ResourceFolderType.LAYOUT
    }

    override fun getApplicableElements(): Collection<String>? {
        return setOf(
            "com.lyft.android.lint.app.DeprecatedCustomView"
        )
    }

    override fun visitElement(context: XmlContext, element: Element) {
        context.report(
            issue = ISSUE,
            scope = element,
            location = context.getNameLocation(element),
            message = "Don't use DeprecatedCustomView!"
        )
    }
}