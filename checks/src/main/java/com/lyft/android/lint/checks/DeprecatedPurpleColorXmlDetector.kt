package com.lyft.android.lint.checks

import com.android.resources.ResourceFolderType
import com.android.resources.ResourceType
import com.android.resources.ResourceUrl
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Attr

/**
 * A custom lint check that prohibits usages of the `@color/deprecated_purple`
 * color resource in layout XML files.
 */
class DeprecatedPurpleColorXmlDetector : ResourceXmlDetector() {

    companion object {
        val ISSUE = Issue.create(
            id = "DeprecatedPurpleColorXml",
            briefDescription = "Prohibits usages of the `deprecated_purple` color resource in layout XML",
            explanation = "The `deprecated_purple` color resource is deprecated and should no longer be used",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                DeprecatedPurpleColorXmlDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }

    override fun appliesTo(folderType: ResourceFolderType): Boolean {
        // Return true if we want to analyze resource files in the specified resource
        // folder type. In this case we only need to analyze layout resource files.
        return folderType == ResourceFolderType.LAYOUT
    }

    override fun getApplicableAttributes(): Collection<String>? {
        // Return the set of attribute names we want to analyze. The `visitAttribute` method
        // below will be called each time lint sees one of these attributes in a
        // layout XML resource file. In this case, we want to analyze every attribute
        // in every layout XML resource file.
        return XmlScannerConstants.ALL
    }

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        // Get the value of the XML attribute.
        val attributeValue = attribute.nodeValue

        // Attempt to parse the attribute value into a resource url reference.
        // Return immediately if the attribute value is not a resource url reference.
        val resourceUrl = ResourceUrl.parse(attributeValue) ?: return

        if (resourceUrl.type != ResourceType.COLOR) {
            // Ignore the attribute value if it isn't a color resource.
            return
        }
        if (resourceUrl.isFramework) {
            // Ignore the attribute value if this is a color resource from the Android framework
            // (i.e. `@android:color/***`).
            return
        }
        if (resourceUrl.name != "deprecated_purple") {
            // Finally, ignore the attribute value if it isn't named "deprecated_purple".
            return
        }

        context.report(
            issue = ISSUE,
            scope = attribute,
            location = context.getValueLocation(attribute),
            message = "The `@color/deprecated_purple` resource is deprecated and should not be used."
        )

    }
}