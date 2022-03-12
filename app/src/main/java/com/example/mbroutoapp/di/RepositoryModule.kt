package com.example.mbroutoapp.di

import android.content.Context
import com.example.mbroutoapp.data.repository.DataStoreOperationImpl
import com.example.mbroutoapp.data.repository.Repository
import com.example.mbroutoapp.domain.repository.DataStoreOperations
import com.example.mbroutoapp.domain.use_cases.UseCases
import com.example.mbroutoapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.example.mbroutoapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreOperation(
        @ApplicationContext context:Context
    ):DataStoreOperations{
        return DataStoreOperationImpl(context =context )
    }


    @Provides
    @Singleton
    fun provideUseCases(repository: Repository):UseCases{

        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository)
        )
    }
}