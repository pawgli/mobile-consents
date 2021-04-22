package com.example.mobileconsents.utils

import androidx.fragment.app.Fragment
import com.example.mobileconsents.MobileConsentsApp

fun Fragment.getMobileConsentSdk() =
    (requireContext().applicationContext as MobileConsentsApp).mobileConsentSdk