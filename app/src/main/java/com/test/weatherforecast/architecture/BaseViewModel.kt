package com.test.weatherforecast.architecture

import android.app.Application
import android.content.Intent
import androidx.annotation.StringRes
import androidx.lifecycle.*
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseViewModel<T : BaseRepository>(application: Application) :
    AndroidViewModel(application), LifecycleObserver {

    protected val compositeDisposable = CompositeDisposable()

    init {
        inject()
    }

    @Inject
    protected lateinit var repository: T

    abstract fun inject()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}

    protected fun getString(@StringRes stringRes: Int): String =
        getApplication<Application>().getString(stringRes)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun ownerOnCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun ownerOnResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun ownerOnStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun ownerOnPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun ownerOnStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun ownerOnDestroy() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    open fun ownerOAny(source: LifecycleOwner, event: Lifecycle.Event) {
    }
}