package com.lyft.android.lint.checks

import com.android.tools.lint.detector.api.*

/**
 * A custom lint check that suggests replacing 'Okay' with 'OK' in string resources.
 */
class OkayStringXmlDetector : ResourceXmlDetector() {

    companion object {
        val ISSUE = Issue.create(
            id = "OkayStringXml",
            briefDescription = "'Okay' should be spelled 'OK' in string resources",
            explanation = "'Okay' should be spelled 'OK' in string resources",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                OkayStringXmlDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }
}