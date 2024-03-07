package com.verianggoro.news.common

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AlertDialog
import com.verianggoro.news.NewsApp


fun Throwable.getUserUnderstandableError(context: Context?): String {
    return OkHttpHelper.getUserUnderstandableError(context ?: NewsApp.instance, this)
}

fun Context?.showErrorDialog(e: Throwable, okListener: (() -> Unit)?): AlertDialog? {
    return showErrorDialog(e.getUserUnderstandableError(this), okListener)
}

fun Context?.showErrorDialog(message: String, okListener: (() -> Unit)?): AlertDialog? {
    return if (this == null) {
        AlertDialog.Builder(NewsApp.instance).create()
    } else {
        AlertDialog.Builder(ContextThemeWrapper(this, com.google.android.material.R.style.Theme_MaterialComponents_Dialog_Alert))
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { _, _ -> okListener?.let { it() }}
            .show()
    }
}

fun Context?.showErrorDialog(e: Exception): AlertDialog? {
    return this?.let {
        return this.showErrorDialog(e, null)
    }
}