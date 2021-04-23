package com.leonard.noteplus.scanner.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leonard.noteplus.scanner.Constants
import com.leonard.noteplus.scanner.data.local.DocScannerDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideGlide(
        @ApplicationContext context: Context
    ): RequestManager = Glide.with(context)

    @Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        DocScannerDb::class.java,
        Constants.DOCSCANNER_DB_NAME
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun providePdfDao(db: DocScannerDb) = db.getPdfDao()

    @Singleton
    @Provides
    fun provideFirestore() = Firebase.firestore


}