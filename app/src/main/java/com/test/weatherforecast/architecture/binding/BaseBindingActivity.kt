package com.test.weatherforecast.architecture.binding

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.test.weatherforecast.architecture.BaseActivity
import com.test.weatherforecast.architecture.BaseViewModel

abstract class BaseBindingActivity<VM : BaseViewModel<*>, B : ViewDataBinding> :
    BaseActivity<VM>() {

    protected lateinit var binding: B
    abstract val layoutId: Int

    abstract fun setDataToBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        setDataToBinding()
    }
}