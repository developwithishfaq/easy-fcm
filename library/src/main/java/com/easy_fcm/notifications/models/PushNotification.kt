package com.easy_fcm.notifications.models

data class PushNotification(
    val notification: NotificationData,
    val to: String
)

data class NotificationData(
    val title: String,
    val body: String
)