package com.th3pl4gu3.mauritius_emergency_services.di

import android.content.Context
import com.th3pl4gu3.mauritius_emergency_services.MesApplication
import com.th3pl4gu3.mauritius_emergency_services.data.AppContainer
import com.th3pl4gu3.mauritius_emergency_services.data.DefaultAppContainer
import com.th3pl4gu3.mauritius_emergency_services.data.local.LocalServiceRepository
import com.th3pl4gu3.mauritius_emergency_services.data.store.StoreRepository
import com.th3pl4gu3.mauritius_emergency_services.ui.wrappers.NetworkRequests
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    private const val TAG = "MODULE_INJECTION_TAG"

    @Provides
    fun providesMesApplicationInstance(@ApplicationContext context: Context): MesApplication {
        return context as MesApplication
    }

    @Provides
    fun providesAppContainer(@ApplicationContext context: Context): AppContainer {
        return DefaultAppContainer(context = context)
    }

    @Provides
    fun providesLocalRepository(container: AppContainer): LocalServiceRepository {
        return container.offlineServiceRepository
    }

    @Provides
    fun providesDataStoreRepository(container: AppContainer): StoreRepository {
        return container.dataStoreServiceRepository
    }

    @Provides
    fun providesNetworkRepository(
        @ApplicationContext context: Context,
        container: AppContainer
    ): NetworkRequests {
        return NetworkRequests(context, container.onlineServiceRepository)
    }
}