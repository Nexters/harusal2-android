package com.nexters.zzallang.harusal2.keyboard.util

import android.widget.EditText

fun EditText.suppressSoftKeyboard() {
    showSoftInputOnFocus = false
}