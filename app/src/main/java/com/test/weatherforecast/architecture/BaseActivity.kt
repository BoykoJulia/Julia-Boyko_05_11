package com.test.weatherforecast.architecture

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.test.weatherforecast.architecture.utils.ViewModelFactory

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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, resultCode, data)
    }
}