package com.easy_fcm.notifications.interfaces

import com.easy_fcm.notifications.models.PushNotification
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface NotificationApi {
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification,
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String
    ): retrofit2.Response<ResponseBody>
}
