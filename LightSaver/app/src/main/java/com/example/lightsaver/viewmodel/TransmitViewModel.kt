package com.example.lightsaver.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import android.text.TextUtils
import com.example.lightsaver.R
import com.example.lightsaver.api.EspApi
import com.example.lightsaver.api.MessageResponse
import com.example.lightsaver.api.NetworkResponse
import com.example.lightsaver.persistence.MessageDao
import com.example.lightsaver.persistence.Preferences
import com.example.lightsaver.util.DateUtils
import com.example.lightsaver.vo.Message
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class TransmitViewModel @Inject
constructor(application: Application, private val dataSource: MessageDao,
            private val configuration: Preferences) : AndroidViewModel(application) {

    private val toastText = ToastMessage()
    private val snackbarText = SnackbarMessage()
    private val alertText = AlertMessage()
    private val networkResponse = MutableLiveData<NetworkResponse>()
    private val disposable = CompositeDisposable()

    fun networkResponse(): MutableLiveData<NetworkResponse> {
        return networkResponse
    }

    fun getToastMessage(): ToastMessage {
        return toastText
    }

    fun getAlertMessage(): AlertMessage {
        return alertText
    }

    fun getSnackbarMessage(): SnackbarMessage {
        return snackbarText
    }

    init {
        // na-da
    }

    private fun showSnackbarMessage(message: Int?) {
        snackbarText.value = message
    }

    private fun showAlertMessage(message: String?) {
        toastText.value = message
    }

    private fun showToastMessage(message: String?) {
        toastText.value = message
    }

    fun sendMessage(message: String) {
        if(!TextUtils.isEmpty(configuration.address())) {
            val api = EspApi(configuration.address()!!)
            disposable.add(api.sendState(message)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { networkResponse.value = NetworkResponse.loading() }
                    .subscribeWith( object : DisposableObserver<MessageResponse>() {
                        override fun onNext(response: MessageResponse) {
                            if (!TextUtils.isEmpty(response.message) && !TextUtils.isEmpty(response.value)){
                                insertMessageResponse(response.message, response.value)
                                networkResponse.value = NetworkResponse.success(response.message)
                            }
                        }
                        override fun onComplete() {
                            Timber.d("complete");
                            networkResponse.value = NetworkResponse.success("complete")
                        }
                        override fun onError(error: Throwable) {
                            networkResponse.value = NetworkResponse.error(error)
                            var errorMessage: String? = "Server error"
                            if(!TextUtils.isEmpty(error.message)) {
                                errorMessage = error.message
                            }
                            insertMessageResponse("error", errorMessage!!)
                            Timber.e("error: " + error.message);
                        }
                    }));
        } else {
            showAlertMessage(getApplication<Application>().getString(R.string.error_empty_address))
        }
    }

    /**
     * Insert new message into the database.
     */
    private fun insertMessageResponse(msg: String, value: String) {
        disposable.add(Completable.fromAction {
                    val createdAt = DateUtils.generateCreatedAtDate()
                    val message  = Message()
                    message.value = value
                    message.message = msg
                    message.createdAt = createdAt
                    dataSource.insertMessage(message)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                }, { error -> Timber.e("Database error" + error.message)}))
    }

    public override fun onCleared() {
        //prevents memory leaks by disposing pending observable objects
        if ( !disposable.isDisposed) {
            disposable.clear()
        }
    }

    /**
     * Network connectivity receiver to notify client of the network disconnect issues and
     * to clear any network notifications when reconnected. It is easy for network connectivity
     * to run amok that is why we only notify the user once for network disconnect with
     * a boolean flag.
     */
    companion object {

    }
}