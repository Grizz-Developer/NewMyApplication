package com.guit.edu.myapplication.net

import com.guit.edu.myapplication.entity.History
import com.guit.edu.myapplication.entity.User
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.List

interface RetrofitApi {
    companion object {
        const val BaseUrl = "http://142.171.116.120:8080/"
    }


    @FormUrlEncoded
    @POST("user/register")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("height") height: Int,
        @Field("weight") weight: Int
    ): Observable<Result<String>>

    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<Result<String>>

    @GET("user/info")
    fun getUserInfo(@Header("Authorization") token: String): Observable<Result<User>>

    @PUT("user/{userId}")
    fun updateUserInfo(@Path("userId") userId: Int, @Body updateUser: User): Observable<User>


    @GET("history/user/{username}")
    fun getUserHistory(
        @Path("username") username: String,
        @Query("date") date: String
    ): Observable<List<History>>

}

