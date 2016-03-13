package com.marcosambrosi.kotlinexperiment.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by marcosambrosi on 3/13/16.
 */
class SentityApiManager {


    //
    //    const val retrofit: Retrofit = Retrofit.Builder().
    //            baseUrl(baseUrl).
    //            build()
    //
    //    val service: Api = retrofit.create(Api.class)

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