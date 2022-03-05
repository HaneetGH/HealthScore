package com.technorapper.onboarding.widgets




inline fun <A, B, R> ifNotNull(a: A?, b: B?, code: (A,B) -> R) {
    if (a != null && b != null)
        code(a,b)
}
