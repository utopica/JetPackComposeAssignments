package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.datasource.ToDosDataSource
import com.example.todoapp.data.repo.ToDosRepository
import com.example.todoapp.room.Database
import com.example.todoapp.room.ToDosDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideToDosRepository(todods: ToDosDataSource): ToDosRepository {
        return ToDosRepository(todods)
    }

    @Provides
    @Singleton
    fun provideToDosDataSource(tododao: ToDosDao): ToDosDataSource {
        return ToDosDataSource(tododao)
    }

    @Provides
    @Singleton
    fun provideToDosDao(@ApplicationContext context: Context) : ToDosDao{
        var db = Room.databaseBuilder(context, Database::class.java, "todoapp.sqlite")
            .createFromAsset("todoapp.sqlite")
            .build()

        return db.getToDosDao()
    }

}

