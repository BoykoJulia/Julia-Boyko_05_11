package com.test.weatherforecast.di

import com.test.weatherforecast.di.components.AppModule
import com.test.weatherforecast.di.components.GsonModule
import com.test.weatherforecast.di.components.NetworkModule
import com.test.weatherforecast.di.components.OkHttpModule
import dagger.Component
import com.test.weatherforecast.di.components.RetrofitModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RetrofitModule::class,
        OkHttpModule::class,
        GsonModule::class,
        NetworkModule::class]
)
interface AppComponent {


    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        fun appModule(appModule: AppModule): Builder
    }
}