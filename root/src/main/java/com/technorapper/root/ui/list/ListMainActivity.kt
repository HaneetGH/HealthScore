package com.technorapper.root.ui.list

import androidx.databinding.DataBindingUtil
import com.technorapper.root.R
import com.technorapper.root.base.BaseClass

import com.technorapper.root.databinding.RootActivityBinding

class ListMainActivity:BaseClass() {
    lateinit var binding: RootActivityBinding

    override fun setBinding() {
        binding =
            DataBindingUtil.setContentView(this, R.layout.root_activity)
    }

    override fun attachViewModel() {
        TODO("Not yet implemented")
    }


}