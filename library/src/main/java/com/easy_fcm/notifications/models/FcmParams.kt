package com.easy_fcm.notifications.models

data class FcmParams(
    val remoteNotificationData: FcmNotification,
    val dataParams: List<Pair<String, Any>>? = emptyList()
)