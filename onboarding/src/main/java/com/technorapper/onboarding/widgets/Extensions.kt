package com.technorapper.onboarding.widgets

import android.content.Context
import android.util.TypedValue
import android.view.View


fun View.dpToPx(dp: Double): Double {
    val scale = context.resources.displayMetrics.density
    return dp * scale + 0.5f
}

fun View.spToPx(sp: Float): Int {
    val px =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics)
            .toInt()
    return px
}


/**
 * возвращает @param [alt], если this == null
 */
fun <T> T?.ifNotNullOr(alt: T): T {
    if (this == null)
        return alt
    else
        return this
}

fun <T> T?.ifNotNullOr(alt: () -> T): T {
    if (this == null)
        return alt()
    else
        return this
}

fun <T> List<T>.plusElementFront(element: T): List<T> {
    val newList = this.toMutableList()
    newList.add(0, element)
    return newList.toList()
}

fun Context.getThemeColor(attr: Int): Int {
    val typedValue: TypedValue = TypedValue()
    this.theme.resolveAttribute(attr, typedValue, true)
    return typedValue.data
}