package com.marcosambrosi.kotlinexperiment.network

import com.marcosambrosi.kotlinexperiment.model.Model
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by marcosambrosi on 3/13/16.
 */
interface SentityService {

    @GET("sentiment")
    fun getSentiment(@Path("text") thought: String): Observable<Model.Sentiment>

    companion object {
        val baseUrl = "https://sentity-v1.p.mashape.com/v1/"

        fun create(): SentityService {
            val restAdapter = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()

            return restAdapter.create(SentityService::class.java);
        }

    }
}