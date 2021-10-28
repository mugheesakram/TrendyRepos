package com.exercise.trendyrepos.utils.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

const val EXTRA = "_bundle_extras"

/**
 * Extensions for simpler launching of Activities
 */
fun Fragment.finishAffinity() {
    activity?.finishAffinity()
}

fun Fragment.finish() {
    activity?.finish()
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)

/**
 * Extension method to get a new Intent for an Activity class
 */
inline fun <reified T : Any> Context.intent() = Intent(this, T::class.java)

/**
 * Create an intent for [T] and apply a lambda on it
 */
inline fun <reified T : Any> Context.intent(body: Intent.() -> Unit): Intent {
    val intent = Intent(this, T::class.java)
    intent.body()
    return intent
}

inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    clearPrevious: Boolean = false,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    intent.putExtra(EXTRA, options)
    if (clearPrevious) finish()
    startActivityForResult(intent, requestCode, options)
}
