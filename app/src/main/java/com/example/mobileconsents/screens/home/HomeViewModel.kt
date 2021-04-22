package com.example.mobileconsents.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookieinformation.mobileconsents.ConsentItem
import com.cookieinformation.mobileconsents.MobileConsentSdk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import timber.log.Timber
import java.util.*

private val DEFAULT_UUID = UUID.fromString("19e927e8-5767-4f11-93dd-4dec4d57456e")

class HomeViewModel(
    private val mobileConsentSdk: MobileConsentSdk
) : ViewModel() {

    val consentUuid: UUID = DEFAULT_UUID

    private val _consentsLoadingStateFlow = MutableStateFlow<LoadingState>(LoadingState.Initial)
    val consentsLoadingStateFlow: StateFlow<LoadingState>
        get() = _consentsLoadingStateFlow

    private val _consentsListStateFlow = MutableStateFlow(ConsentsListState.HIDDEN)
    val consentsListStateFlow: StateFlow<ConsentsListState>
        get() = _consentsListStateFlow

    fun fetchConsents() = viewModelScope.launch {
        _consentsListStateFlow.value = ConsentsListState.HIDDEN
        _consentsLoadingStateFlow.value = LoadingState.Loading
        try {
            val solution = mobileConsentSdk.fetchConsentSolution(consentUuid)
            _consentsLoadingStateFlow.value = LoadingState.Loaded(solution.consentItems)
            _consentsListStateFlow.value = ConsentsListState.SHOWN
        } catch (e: IOException) {
            Timber.w(e)
            _consentsLoadingStateFlow.value = LoadingState.Failure
        }
    }

    fun switchConsentsListState() {
        _consentsListStateFlow.value = when (consentsListStateFlow.value) {
            ConsentsListState.HIDDEN -> ConsentsListState.SHOWN
            else -> ConsentsListState.HIDDEN
        }
    }
}

sealed class LoadingState {
    object Initial : LoadingState()
    object Loading : LoadingState()
    data class Loaded(val consentItems: List<ConsentItem>) : LoadingState()
    object Failure : LoadingState()
}

enum class ConsentsListState {
    HIDDEN,
    SHOWN
}