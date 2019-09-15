package com.lyft.android.ui

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton

/**
 * A new-and-improved button class.
 *
 * Existing usages of [DeprecatedButton] should migrate to use this button class instead.
 */
class PrettyButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : MaterialButton(context, attrs)