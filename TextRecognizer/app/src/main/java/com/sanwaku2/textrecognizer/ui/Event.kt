package com.sanwaku2.textrecognizer.ui

class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? =
        if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
}