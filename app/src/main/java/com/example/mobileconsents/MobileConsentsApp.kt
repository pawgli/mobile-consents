package com.example.mobileconsents

import android.app.Application
import com.cookieinformation.mobileconsents.MobileConsentSdk
import okhttp3.OkHttpClient
import timber.log.Timber

class MobileConsentsApp : Application() {

    internal val mobileConsentSdk by lazy {
        MobileConsentSdk.Builder(this)
            .partnerUrl("https://consents-gathering-app-staging.app.cookieinformation.com")
            .callFactory(OkHttpClient())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}