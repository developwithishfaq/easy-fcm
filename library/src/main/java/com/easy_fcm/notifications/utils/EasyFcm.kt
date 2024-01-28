package com.easy_fcm.notifications.utils

import com.easy_fcm.notifications.interfaces.FcmHelperCallBacks
import com.easy_fcm.notifications.models.FcmParams
import com.easy_fcm.notifications.models.SendType

class EasyFcm(
    private val serverKey: String
) {
    private val apiWrapper = ApiWrapper()

    fun pushTestNotification(sendType: SendType, callBacks: FcmHelperCallBacks? = null) {
        apiWrapper.sendNotification(
            sendType = sendType,
            serverKey = serverKey,
            callBacks = callBacks
        )
    }

    fun pushNotification(
        params: FcmParams,
        sendType: SendType,
        callBacks: FcmHelperCallBacks? = null,
    ) {
        apiWrapper.sendNotification(params, sendType, serverKey, callBacks)
    }
}