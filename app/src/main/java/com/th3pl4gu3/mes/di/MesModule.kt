package com.th3pl4gu3.mes.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.th3pl4gu3.mes.MesApplication
import com.th3pl4gu3.mes.data.AppContainer
import com.th3pl4gu3.mes.data.DefaultAppContainer
import com.th3pl4gu3.mes.models.MesAppSettings
import com.th3pl4gu3.mes.models.MesAppSettingsSerializer
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