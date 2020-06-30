package com.damianopatane.weatherapplication.di.module

import android.content.Context
import com.damianopatane.weatherapplication.data.source.local.DatabaseRealm
import com.damianopatane.weatherapplication.helpers.ModelConverter
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [NetworkModule::class,
                    ApplicationModule::class])
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabaseRealm(gson : Gson,
                             context : Context
    ): DatabaseRealm {
        return DatabaseRealm(gson, context)
    }

    @Provides
    @Singleton
    fun provideConverter(gson : Gson,
                         databaseRealm : DatabaseRealm): ModelConverter {
        return ModelConverter(gson, databaseRealm)
    }
}