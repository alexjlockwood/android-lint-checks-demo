package com.lyft.android.lint.checks

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Issue

/**
 * The list of issues that will be checked when running lint.
 *
 * This class is referenced from this module's build.gradle file,
 * so we can suppress the unused warning.
 */
@Suppress("unused")
class IssueRegistry : IssueRegistry() {

    override val api: Int = com.android.tools.lint.detector.api.CURRENT_API

    override val issues: List<Issue> = listOf()
}
