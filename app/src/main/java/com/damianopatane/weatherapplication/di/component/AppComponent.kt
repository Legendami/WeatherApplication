package com.damianopatane.weatherapplication.di.component

import android.app.Application
import android.content.Context
import dagger.Component
import com.damianopatane.weatherapplication.di.module.ViewModelModule
import com.damianopatane.weatherapplication.di.module.*
import com.damianopatane.weatherapplication.domain.repository.WeatherDataRepository
import dagger.BindsInstance
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        DatabaseModule::class,
        NetworkModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication>{

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}