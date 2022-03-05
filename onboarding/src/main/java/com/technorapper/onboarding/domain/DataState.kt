package com.technorapper.onboarding.domain

import com.technorapper.onboarding.constant.Task


sealed class DataState {

    data class Success<out T>(val data: T, val task: Task) : DataState()
    data class SuccessRes<out T>(val data: T) : DataState()
    data class Progress<out T>(val data: T,val task: Task) : DataState()
    data class Error(val exception: Exception, val task: Task) : DataState()
    data class ErrorThrowable(val exception: Throwable, val task: Task) : DataState()
    data class Loading(val task: Task) : DataState()
}
