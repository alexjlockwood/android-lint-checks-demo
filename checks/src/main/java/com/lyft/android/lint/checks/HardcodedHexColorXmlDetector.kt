package com.lyft.android.lint.checks

import com.android.tools.lint.detector.api.*

/**
 * A custom lint check that prohibits hardcoded hex colors in layout XML.
 */
class HardcodedHexColorXmlDetector : ResourceXmlDetector() {

    companion object {
        val ISSUE = Issue.create(
            id = "HardcodedHexColorXml",
            briefDescription = "Prohibits hardcoded hex colors in layout XML",
            explanation = "Hex colors should be declared in a <color> resource",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                HardcodedHexColorXmlDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }
}























