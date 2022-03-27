package com.technorapper.root.ui.list

import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController

import com.technorapper.root.R
import com.technorapper.root.base.BaseClass
import com.technorapper.root.databinding.RootActivityBinding
import kotlinx.coroutines.launch


class RootActivity : BaseClass() {


    private val viewModel by viewModels<ListActivityViewModel>()
    lateinit var binding: RootActivityBinding


    override fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.root_activity)
        binding.bottomNavigation.setupWithNavController(
            Navigation.findNavController(
                this,
                R.id.fragmentContainerView
            )
        )
        lifecycleScope.launch {
            viewModel.getUser()
        }
        viewModel.showFlow()

    }


    override fun attachViewModel() {
        //viewModel.setStateEvent(MainListStateEvent.FetchBookmark)
        //    viewModel.uiState.observe(this, Observer { parse(it) })
    }


}


