package com.th3pl4gu3.mauritius_emergency_services.di

import android.content.Context
import android.net.ConnectivityManager
import com.th3pl4gu3.mauritius_emergency_services.MesApplication
import com.th3pl4gu3.mauritius_emergency_services.data.AppContainer
import com.th3pl4gu3.mauritius_emergency_services.data.DefaultAppContainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    fun providesMesApplicationInstance(@ApplicationContext context: Context): MesApplication {
        return context as MesApplication
    }

    @Provides
    fun providesAppContainer(@ApplicationContext context: Context): AppContainer {
        return DefaultAppContainer(context = context)
    }
}