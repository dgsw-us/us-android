package kr.us.us_android.util

import android.content.Context
import android.widget.Toast

internal fun Context.shortToast(text: String) =
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

internal fun Context.longToast(text: String) =
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()