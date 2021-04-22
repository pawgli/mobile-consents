package com.example.mobileconsents.screens.privacy

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.cookieinformation.mobileconsents.ConsentSolution
import com.cookieinformation.mobileconsents.ui.BasePrivacyCenterFragment
import com.cookieinformation.mobileconsents.ui.ConsentSolutionBinder
import com.example.mobileconsents.utils.getMobileConsentSdk
import timber.log.Timber
import java.util.*

class PrivacyCenterFragment : BasePrivacyCenterFragment() /* or BasePrivacyPreferencesDialogFragment */ {

    override fun bindConsentSolution(builder: ConsentSolutionBinder.Builder): ConsentSolutionBinder {
        val mobileConsentSdk = getMobileConsentSdk()
        val consentSolutionId =
            PrivacyCenterFragmentArgs.fromBundle(requireArguments()).consentSolutionId
        return builder
            .setMobileConsentSdk(mobileConsentSdk)
            .setConsentSolutionId(consentSolutionId)
            .create()
    }

    override fun onConsentsChosen(consentSolution: ConsentSolution, consents: Map<UUID, Boolean>, external: Boolean) {
        findNavController().navigateUp()
    }

    override fun onDismissed() {
        findNavController().navigateUp()
    }

    override fun onReadMore() {
        // This callback in not called for "BasePrivacyCenterFragment" yet
        // For "BasePrivacyPreferencesDialogFragment" should navigate to "BasePrivacyCenterFragment"
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }
}