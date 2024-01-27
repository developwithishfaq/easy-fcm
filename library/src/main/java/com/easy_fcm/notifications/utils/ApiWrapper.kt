package com.easy_fcm.notifications.utils

import com.easy_fcm.notifications.interfaces.NotificationApi
import com.easy_fcm.notifications.models.NotificationData
import com.easy_fcm.notifications.models.PushNotification
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.easy_fcm.notifications.interfaces.FcmHelperCallBacks
import com.easy_fcm.notifications.models.FcmNotification
import com.easy_fcm.notifications.models.FcmParams
import com.easy_fcm.notifications.models.SendType

internal class ApiWrapper {

    private var listener: FcmHelperCallBacks? = null

    private fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl("https://fcm.googleapis.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun getAPi(): NotificationApi {
        return getInstance().create(NotificationApi::class.java)
    }

    fun sendNotification(
        params: FcmParams = FcmParams(FcmNotification("Test Title","Test Body")),
        sendType: SendType,
        serverKey: String,
        callBacks: FcmHelperCallBacks? = null,
    ) {
        listener = callBacks
        val mToken = when (sendType) {
            is SendType.Topic -> {
                "/topics/${sendType.topicName.replace("/topics/", "")}"
            }

            is SendType.Token -> {
                "key=${sendType.token.replace("key=", "")}"
            }
        }
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            listener?.onError(throwable.message.toString(), -1)
        }

        val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob() + exceptionHandler)
        val mainScope = CoroutineScope(Dispatchers.Main)

        val extras: HashMap<String, Any> = hashMapOf()
        params.dataParams?.forEach { it ->
            extras[it.first] = it.second
        }

        ioScope.launch {
            val notification = params.remoteNotificationData
            val response = getAPi().postNotification(
                notification = PushNotification(
                    NotificationData(notification.title, notification.message), mToken,
                    data = extras
                ),
                contentType = "application/json",
                authorization = serverKey,
            )
            if (response.isSuccessful) {
                mainScope.launch {
                    listener?.onSuccess(
                        message = response.message(),
                        code = response.code()
                    )
                }
            } else {
                mainScope.launch {
                    listener?.onError(
                        message = response.message(),
                        code = response.code()
                    )
                }
            }
        }

    }

}