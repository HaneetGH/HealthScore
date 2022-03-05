package com.technorapper.root.di.scopes

import javax.inject.Scope

@Scope
@kotlin.annotation.Retention
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
annotation class BindingScoped

