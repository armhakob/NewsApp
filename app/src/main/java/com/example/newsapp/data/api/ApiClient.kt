package com.example.newsapp.data.api

import com.example.newsapp.data.model.Multimedia
import com.example.newsapp.data.model.MultimediaDeserializer
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://api.nytimes.com/"

    fun provideRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .registerTypeAdapter(
                object : TypeToken<List<Multimedia>>() {}.type,
                MultimediaDeserializer()
            )
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}

