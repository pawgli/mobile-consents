package com.example.mobileconsents.screens.privacy

import androidx.navigation.fragment.findNavController
import com.cookieinformation.mobileconsents.ConsentSolution
import com.cookieinformation.mobileconsents.ui.BasePrivacyPreferencesDialogFragment
import com.cookieinformation.mobileconsents.ui.ConsentSolutionBinder
import com.example.mobileconsents.utils.getMobileConsentSdk
import java.util.*


class PrivacyPreferencesDialogFragment : BasePrivacyPreferencesDialogFragment() {

    private lateinit var consentSolutionId: UUID

    override fun bindConsentSolution(builder: ConsentSolutionBinder.Builder): ConsentSolutionBinder {
        val mobileConsentSdk = getMobileConsentSdk()
        consentSolutionId =
            PrivacyPreferencesDialogFragmentArgs.fromBundle(requireArguments()).consentSolutionId
        return builder
            .setMobileConsentSdk(mobileConsentSdk)
            .setConsentSolutionId(consentSolutionId)
            .create()
    }

    override fun onConsentsChosen(consentSolution: ConsentSolution, consents: Map<UUID, Boolean>, external: Boolean) {
        dismiss()
    }

    override fun onDismissed() {
        dismiss()
    }

    override fun onReadMore() {
        dismiss()
        findNavController().navigate(
            PrivacyPreferencesDialogFragmentDirections.openPrivacyCenter(consentSolutionId)
        )
    }
}