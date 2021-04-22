package com.example.mobileconsents.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cookieinformation.mobileconsents.MobileConsentSdk
import java.lang.IllegalArgumentException

class HomeViewModelFactory(
    private val mobileConsentSdk: MobileConsentSdk
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(mobileConsentSdk) as T
        }
        throw IllegalArgumentException("Invalid ViewModel class")
    }
}