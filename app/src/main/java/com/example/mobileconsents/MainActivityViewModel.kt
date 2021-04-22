package com.example.mobileconsents

import androidx.lifecycle.ViewModel
import com.example.mobileconsents.utils.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivityViewModel : ViewModel() {

    private val _currentThemeFlow = MutableStateFlow(Event(Theme.PINK))
    val currentThemeFlow: StateFlow<Event<Theme>>
        get() = _currentThemeFlow

    fun switchTheme() {
        val nextTheme =
            if (currentThemeFlow.value.getContent() == Theme.PINK) Theme.BLUE else Theme.PINK
        _currentThemeFlow.value = Event(nextTheme)
    }
}

enum class Theme {
    PINK,
    BLUE
}