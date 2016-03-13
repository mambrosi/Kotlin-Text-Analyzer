package com.marcosambrosi.kotlinexperiment.network

import com.marcosambrosi.kotlinexperiment.model.Model
import rx.Observable

/**
 * Created by marcosambrosi on 3/13/16.
 */
interface SentityService {

    fun getQuote(key: Integer): Observable<Model.Sentiment>
}