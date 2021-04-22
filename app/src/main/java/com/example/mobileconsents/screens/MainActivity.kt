package com.example.mobileconsents.screens

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.mobileconsents.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val viewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpNavigation()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.currentThemeFlow.onEach {
            it.getContentIfNotHandled()?.let { recreate() }
        }.launchIn(lifecycleScope)
    }

    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentNavHost) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun getTheme(): Resources.Theme {
        val style = when (viewModel.currentThemeFlow.value.getContent()) {
            Theme.PINK -> R.style.PinkTheme
            Theme.BLUE -> R.style.BlueTheme
        }
        return super.getTheme().apply { applyStyle(style, true) }
    }

    fun switchTheme() = viewModel.switchTheme()
}