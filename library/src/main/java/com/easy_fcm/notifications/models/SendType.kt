package com.easy_fcm.notifications.models

sealed class SendType {
    /** Just Provide Token, Don't include any Other Symbols**/
    data class Token(val token: String) : SendType()
    /** Just Provide Topic Name, Don't include any other thing**/
    data class Topic(val topicName: String) : SendType()
}