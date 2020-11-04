package com.test.weatherforecast.architecture

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.test.weatherforecast.architecture.utils.ViewModelFactory
import com.test.weatherforecast.utils.live_data.EventObserver

abstract class BaseActivity<VM : BaseViewModel<*>> : AppCompatActivity() {

    protected val viewModel: VM by lazy { createViewModel() }
    abstract fun createViewModel(): VM

    protected inline fun <reified VMP : VM> provideViewModel(noinline instance: (application: Application) -> VMP) =
        ViewModelProvider(
            this,
            ViewModelFactory(
                application,
                instance
            )
        )
            .get(VMP::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(viewModel)
        observeData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, resultCode, data)
    }

    open fun observeData() {
        viewModel.ldAction.observe(this, EventObserver {
            handleAction(it.first, it.second)
        })
    }

    open fun handleAction(action: String, data: Bundle?) {
        when (action) {
            BaseViewModel.ACTION_SHOW_TOAST -> {
                val text = data?.getString(BaseViewModel.PARAM_TOAST_TEXT)
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
            }
        }
    }
}