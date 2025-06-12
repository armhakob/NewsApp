package com.example.newsapp.data.api

import com.example.newsapp.data.model.Multimedia
import com.example.newsapp.data.model.MultimediaDeserializer
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//object RetrofitInstance {
//    private const val BASE_URL = "https://api.nytimes.com/svc/"
//
//    val api: NYTimesApi by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(NYTimesApi::class.java)
//    }
//}

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")  // NYTimes API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: NYTimesApi by lazy {
        retrofit.create(NYTimesApi::class.java)
    }
}