package com.easy_fcm.notifications.interfaces

interface FcmHelperCallBacks {
    fun onSuccess(message: String, code: Int)
    fun onError(message: String, code: Int)
}