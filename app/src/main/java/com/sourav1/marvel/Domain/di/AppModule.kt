package com.sourav1.marvel.Domain.di

import android.app.Application
import androidx.room.Room
import com.sourav1.marvel.Data.Api.MarvelApi
import com.sourav1.marvel.Data.Database.DbInstance
import com.sourav1.marvel.Domain.Repository.Repository
import com.sourav1.marvel.Util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getDbInstance(app: Application): DbInstance {
        return Room.databaseBuilder(
            app,
            DbInstance::class.java,
            "Marvel_DB"
        ).build()
    }


    @Provides
    @Singleton
    fun getRetrofitInstance(): MarvelApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarvelApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMyRepository(api: MarvelApi, dbInstance: DbInstance): Repository {
        return Repository(api, dbInstance)
    }
}