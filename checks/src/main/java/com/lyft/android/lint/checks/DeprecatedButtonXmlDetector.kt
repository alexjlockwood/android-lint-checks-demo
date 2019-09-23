package com.lyft.android.lint.checks

import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Element

/**
 * A custom lint check that prohibits usages of `DeprecatedButton` in layout XML.
 */
class DeprecatedButtonXmlDetector : ResourceXmlDetector() {

    companion object {
        val ISSUE = Issue.create(
            id = "DeprecatedButtonXml",
            briefDescription = "Don't use `DeprecatedButton` in layout XML",
            explanation = "`DeprecatedButton` is deprecated and should no longer be used",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                DeprecatedButtonXmlDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }

    override fun appliesTo(folderType: ResourceFolderType): Boolean {
        // Return true if we want to analyze resource files in the specified resource
        // folder type. In this case we only need to analyze layout resource files.
        return folderType == ResourceFolderType.LAYOUT
    }

    override fun getApplicableElements(): Collection<String>? {
        // Return the set of elements we want to analyze. The `visitElement` method
        // below will be called each time lint sees one of these elements in a
        // layout XML resource file.
        return setOf("com.lyft.android.ui.DeprecatedButton")
    }

    override fun visitElement(context: XmlContext, element: Element) {
        context.report(
            issue = ISSUE,
            scope = element,
            location = context.getNameLocation(element),
            message = "`DeprecatedButton` should not be used."
        )
    }
}