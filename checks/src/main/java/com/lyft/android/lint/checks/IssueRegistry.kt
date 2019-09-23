package com.lyft.android.lint.checks

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

/**
 * The class contains the list of issues that will be checked when running lint.
 *
 * This class is referenced from the `checks/build.gradle` file, so we can
 * suppress the unused warning.
 */
@Suppress("unused")
class IssueRegistry : IssueRegistry() {

    override val api: Int = CURRENT_API

    override val issues: List<Issue> = listOf(
        AndroidToastJavaKotlinDetector.ISSUE,
        HardcodedHexColorXmlDetector.ISSUE,
        OkayStringXmlDetector.ISSUE
    )
}
