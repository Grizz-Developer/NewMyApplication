package com.guit.edu.myapplication

import com.google.gson.GsonBuilder
import com.guit.edu.myapplication.entity.LocalDateDeserializer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtil {
    private const val BASE_URL = "http://142.171.116.120:8080/"
    private val gson = GsonBuilder()
        .registerTypeAdapter(String::class.java, LocalDateDeserializer())
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    val api: RetrofitApi = retrofit.create(RetrofitApi::class.java) // 使用 RetrofitApi
}
