package com.easy_fcm.notifications.models

internal data class PushNotification(
    val notification: NotificationData,
    val to: String,
    val data : HashMap<String,Any>
)
