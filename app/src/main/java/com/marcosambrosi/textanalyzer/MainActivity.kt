package com.marcosambrosi.textanalyzer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.marcosambrosi.textanalyzer.model.Model
import com.marcosambrosi.textanalyzer.network.SentityService
import kotlinx.android.synthetic.main.activity_main.*
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class MainActivity : AppCompatActivity() {

    lateinit var service: SentityService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        service = SentityService.create();

        sendThoughtButton.setOnClickListener {


            showLoadingMode()

            manageSubscription(
                    service.getSentiment(thought?.text.toString()).
                            subscribeOn(Schedulers.io()).
                            observeOn(AndroidSchedulers.mainThread()).
                            subscribe(
                                    { t -> showResult(t) },
                                    { e -> showResult(e) })
            );

        }


    }

    fun showEnterMode() {
        thought.visibility = View.VISIBLE
        progressContainer.visibility = View.GONE
        message.visibility = View.GONE
        sendThoughtButton.visibility = View.VISIBLE;
    }


    fun showLoadingMode() {
        thought.visibility = View.INVISIBLE
        progressContainer.visibility = View.VISIBLE
        message.visibility = View.GONE
        sendThoughtButton.visibility = View.GONE;
    }

    fun showResult(sentiment: Model.Sentiment) {
        thought.visibility = View.INVISIBLE
        sendThoughtButton.visibility = View.INVISIBLE
        progressContainer.visibility = View.GONE

        message.visibility = View.VISIBLE

        message.text = sentiment.getMessage();
    }

    fun showResult(error: Throwable) {
        thought.visibility = View.INVISIBLE;
        sendThoughtButton.visibility = View.INVISIBLE;
        progressContainer.visibility = View.GONE;
        message.visibility = View.VISIBLE
        message.text = getString(R.string.error);
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

    fun Model.Sentiment.getMessage(): String {
        if (pos > neg) {
            return getString(R.string.positive_message);
        } else if (neg > pos) {
            return getString(R.string.negative_message);
        } else {
            return getString(R.string.neutral_message);
        }
        return "";
    }

    override fun onBackPressed() {
        if (message.visibility == View.VISIBLE) {
            showEnterMode()
        } else {
            super.onBackPressed()
        }
    }


}
