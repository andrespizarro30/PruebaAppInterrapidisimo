package com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network

import com.afsoftwaresolutions.pruebaappinterrapidisimo.BuildConfig.BASE_URL
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.core.interceptors.AuthInterceptor
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.providers.RepositoryServiceImp
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.RepositoryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {

        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)

        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            //.addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideRepository(apiService: ApiService):RepositoryService{
        return RepositoryServiceImp(apiService)
    }

}