package ru.aviasales.extension

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun ViewGroup.inflateToRoot(
    @LayoutRes layoutRes: Int
): View = View.inflate(context, layoutRes, this)