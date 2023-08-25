package com.easy_fcm.notifications.utils

import com.easy_fcm.notifications.interfaces.NotificationApi
import com.easy_fcm.notifications.models.NotificationData
import com.easy_fcm.notifications.models.PushNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FcmPushHelper private constructor(
    private val notiData: NotificationData,
    private val serverKey: String,
    private val token: String,
) {

    class Builder {
        private var notiData: NotificationData? = null
        private var serverKey: String? = null
        private var token: String? = null

        fun setNotificationData(notification: NotificationData): Builder {
            this.notiData = notification
            return this
        }

        fun setServerKey(key: String): Builder {
            this.serverKey = key
            return this
        }

        fun setTokenOrTopic(value: String, isTopic: Boolean): Builder {
            if (isTopic) {
                this.token = "/topics/${value.replace("/topics/", "")}"
            } else {
                this.token = "key=${value.replace("key=", "")}"
            }
            return this
        }

        fun build(): FcmPushHelper {
            return FcmPushHelper(
                notiData ?: NotificationData("", ""),
                "key=$serverKey" ?: "",
                token ?: ""
            )
        }
    }

    fun pushNotification(onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        val ioScope = CoroutineScope(Dispatchers.IO)
        val mainScope = CoroutineScope(Dispatchers.Main)
        ioScope.launch {
            val response = getAPi().postNotification(
                PushNotification(notiData, token),
                "application/json",
                serverKey,
            )
            if (response.isSuccessful) {
                mainScope.launch {
                    onSuccess(response.errorBody().toString().replace("http", "0.0"))
                }
            } else {
                mainScope.launch {
                    onError(response.errorBody().toString())
                }
            }
        }
    }


    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getAPi(): NotificationApi {
        return getInstance().create(NotificationApi::class.java)
    }
}