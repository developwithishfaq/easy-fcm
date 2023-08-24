package com.easy_fcm.notifications.interfaces

interface FcmResponse {
    fun onSuccess(e: String)
    fun onError(e: String)
}