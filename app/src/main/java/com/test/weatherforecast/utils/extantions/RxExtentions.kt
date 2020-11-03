package com.test.weatherforecast.utils.extantions

import android.content.Context
import com.test.weatherforecast.utils.InternetUnreachableException
import com.test.weatherforecast.utils.NetworkUtils
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun Completable.applySchedulers(): Completable {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.applySchedulers(): Single<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}


fun <T> Single<T>.checkInternetConnection(context: Context?): Single<T> {
    return Single.fromCallable { NetworkUtils.isInternetAvailable(context) }
        .flatMap {
            if (NetworkUtils.isInternetAvailable(context).not()) {
                Single.error<T>(InternetUnreachableException())
            } else this
        }
}

fun Completable.checkInternetConnection(context: Context?): Completable {
    return Single.fromCallable { NetworkUtils.isInternetAvailable(context) }
        .flatMapCompletable {
            if (NetworkUtils.isInternetAvailable(context).not()) {
                Completable.error(InternetUnreachableException())
            } else this
        }
}

fun <T> Observable<T>.applySchedulers(): Observable<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.checkInternetConnection(context: Context?): Observable<T> {
    return Observable.fromCallable { NetworkUtils.isInternetAvailable(context) }
        .flatMap {
            if (NetworkUtils.isInternetAvailable(context).not()) {
                Observable.error<T>(InternetUnreachableException())
            } else this
        }
}

fun CompositeDisposable.add(disposable: Disposable?) {
    disposable?.let { add(it) }
}