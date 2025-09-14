package com.example.noteapp.di

import android.content.Context
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.Room
import com.example.noteapp.data.local.db.MyDataBase
import com.example.noteapp.data.local.db.NotesDao
import com.example.noteapp.data.local.db.NotesRepository
import com.example.noteapp.viewModel.NotesViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMyDataBase(@ApplicationContext context: Context): MyDataBase{
        return Room.databaseBuilder(
            context,
            MyDataBase::class.java,
            MyDataBase.DB_NAME
        ).build()
    }

    @Provides
    fun provideNotesDao(myDataBase: MyDataBase) : NotesDao =
        myDataBase.notesDao()

}