package com.marcosambrosi.kotlinexperiment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.marcosambrosi.kotlinexperiment.model.Model
import com.marcosambrosi.kotlinexperiment.network.SentityService
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class MainActivity : AppCompatActivity() {

    var thought: EditText? = null
    var sendThoughtButton: Button? = null;

    lateinit var service: SentityService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        service = SentityService.create();

        sendThoughtButton?.setOnClickListener {

            fun showLoadingMode() {
                thought?.visibility = View.INVISIBLE;
            }

            fun showResult(sentiment: Model.Sentiment) {

            }

            fun showResult(error: Throwable) {

            }

            showLoadingMode()


            manageSubscription(

                    service.getSentiment(thought?.text.toString()).
                            subscribeOn(Schedulers.io()).
                            observeOn(AndroidSchedulers.mainThread()).
                            subscribe({ t -> showResult(t) },
                            { e -> showResult(e) })


            );

        }


    }

    private var _compoSub = CompositeSubscription()
    private val compoSub: CompositeSubscription
        get() {
            if (_compoSub.isUnsubscribed) {
                _compoSub = CompositeSubscription()
            }
            return _compoSub
        }

    protected final fun manageSubscription(s: Subscription) = compoSub.add(s)
}
