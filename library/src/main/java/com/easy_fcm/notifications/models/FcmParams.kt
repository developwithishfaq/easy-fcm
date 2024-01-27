package com.easy_fcm.notifications.models

import com.easy_fcm.notifications.models.FcmNotification

data class FcmParams(
    val remoteNotificationData: FcmNotification,
    val dataParams: List<Pair<String, Any>>? = emptyList()
)