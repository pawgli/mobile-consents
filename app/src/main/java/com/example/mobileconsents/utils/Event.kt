package com.example.mobileconsents.utils

class Event<out T>(private val content: T) {

    private var isHandled = false

    fun getContentIfNotHandled(): T? {
        return if (isHandled) {
            null
        } else {
            isHandled = true
            return content
        }
    }

    fun getContent() = content
}