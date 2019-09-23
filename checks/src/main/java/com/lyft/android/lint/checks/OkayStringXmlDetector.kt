package com.lyft.android.lint.checks

import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Element
import org.w3c.dom.Node


/**
 * A custom lint check that suggests replacing 'Okay' with 'OK' in string resources.
 */
class OkayStringXmlDetector : ResourceXmlDetector() {

    companion object {
        val ISSUE = Issue.create(
            id = "OkayStringXml",
            briefDescription = "Detects usages of 'Okay' in string resources",
            explanation = "The word 'OK' should be used instead of 'Okay' in string resources",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                OkayStringXmlDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }

    override fun appliesTo(folderType: ResourceFolderType): Boolean {
        // Return true if we want to analyze resource files in the specified resource
        // folder type. In this case we only need to analyze strings in the 'values'
        // resource folder.
        return folderType == ResourceFolderType.VALUES
    }

    override fun getApplicableElements(): Collection<String>? {
        // Return the set of elements we want to analyze. In this case we want to
        // analyze every `<string>` element that is declared in XML.
        return setOf("string")
    }

    override fun visitElement(context: XmlContext, element: Element) {
        if (!element.hasChildNodes()) {
            // <string> elements should always have a single child node
            // (the string text), but double check just to be safe.
            return
        }

        val textNode = element.firstChild
        if (textNode.nodeType != Node.TEXT_NODE) {
            // The first child of a `<string>` element should always be a text
            // node, but double check just to be safe.
            return
        }

        val stringText = textNode.nodeValue
        if (!stringText.contains("Okay")) {
            // Do nothing if the string doesn't contain the word 'Okay'.
            return
        }

        context.report(
            issue = ISSUE,
            scope = element,
            location = context.getNameLocation(textNode),
            message = "'Okay' should be spelled 'OK' in string resources.",
            // Pro tip: optionally specify a `LintFix` when reporting issues to propose
            // quick-fixes directly in the IDE! Watch Tor's lint talk for more
            // information: https://j.mp/lint-in-depth
            quickfixData = LintFix.create()
                .replace()
                .text("Okay")
                .with("OK")
                .build()
        )
    }
}