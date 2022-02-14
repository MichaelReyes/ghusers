package com.technologies.ghusers.core.di

import android.content.Context
import com.technologies.ghusers.core.data.dao.NoteDao
import com.technologies.ghusers.core.data.dao.UserDao
import com.technologies.ghusers.core.network.UsersRepository
import com.technologies.ghusers.core.network.UsersService
import com.technologies.ghusers.core.utils.NetworkHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideUsersRepository(
        @ApplicationContext context: Context,
        service: UsersService,
        userDao: UserDao,
        noteDao: NoteDao,
        networkHandler: NetworkHandler
    ): UsersRepository {
        return UsersRepository.UsersRepositoryImpl(
            context, service,
            userDao, noteDao,
            networkHandler
        )
    }

}