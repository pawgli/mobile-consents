package com.example.mobileconsents.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cookieinformation.mobileconsents.ConsentItem
import com.example.mobileconsents.R
import com.example.mobileconsents.screens.MainActivity
import com.example.mobileconsents.databinding.FragmentHomeBinding
import com.example.mobileconsents.utils.getMobileConsentSdk
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val MAX_PROGRESS = 1.0f

class HomeFragment : Fragment() {

    private lateinit var viewBinding: FragmentHomeBinding
    private val viewModel by lazy {
        val viewModelFactory = HomeViewModelFactory(getMobileConsentSdk())
        ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentHomeBinding.inflate(inflater, container, false).run {
            viewBinding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialStateOfConsentsList()
        setUpButtons()
        observeViewModel()
    }

    private fun setInitialStateOfConsentsList() {
        if (viewModel.consentsListStateFlow.value == ConsentsListState.SHOWN) {
            viewBinding.root.progress = MAX_PROGRESS
        }
    }

    private fun setUpButtons() {
        viewBinding.switchThemeButton.setOnClickListener {
            (activity as MainActivity).switchTheme()
        }
        viewBinding.privacyPreferencesButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.showPrivacyPreferencesDialog(viewModel.consentUuid)
            )
        }
        viewBinding.privacyCenterButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.openPrivacyCenter(viewModel.consentUuid)
            )
        }
        viewBinding.fetchConsentsButton.setOnClickListener { viewModel.fetchConsents() }
        viewBinding.consentsListControlButton.setOnClickListener {
            viewModel.switchConsentsListState()
        }
    }


    private fun observeViewModel() {
        observeConsentsListState()
        observeConsentsLoadingState()
    }

    private fun observeConsentsListState() {
        viewModel.consentsListStateFlow.onEach { consentsListState ->
            when (consentsListState) {
                ConsentsListState.HIDDEN -> hideConsentsList()
                else -> showConsentsList()
            }
        }.launchIn(lifecycleScope)
    }

    private fun hideConsentsList() {
        if (isConsentsListHidden().not()) viewBinding.root.transitionToStart()
        viewBinding.consentsListControlButton.text = resources.getString(R.string.showConsentsLabel)
    }

    private fun showConsentsList() {
        if (isConsentsListHidden()) viewBinding.root.transitionToEnd()
        viewBinding.consentsListControlButton.text = resources.getString(R.string.hideConsentsLabel)
    }

    private fun observeConsentsLoadingState() {
        viewModel.consentsLoadingStateFlow.onEach { loadingState ->
            when (loadingState) {
                LoadingState.Loading -> onItemsLoading()
                LoadingState.Failure -> onItemsFetchingFailure()
                is LoadingState.Loaded -> onItemsLoaded(loadingState.consentItems)
                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    private fun onItemsLoading() {
        viewBinding.progressBar.isVisible = true
    }

    private fun onItemsFetchingFailure() {
        viewBinding.progressBar.isVisible = false
        Toast.makeText(requireContext(), getString(R.string.fetchingDataFailed), Toast.LENGTH_SHORT).show()
    }

    private fun onItemsLoaded(consentItems: List<ConsentItem>) {
        val itemsAdapter = ConsentItemAdapter(consentItems)
        viewBinding.consentsList.adapter = itemsAdapter
    }

    private fun isConsentsListHidden() = viewBinding.root.startState == viewBinding.root.currentState
}