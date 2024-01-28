# Easy FCM
Using this library we can Easily Push Notifications to clients without any headache of setting up a system for this feature.

## Setup
> Step 1: Add it to your build.gradle/setting gradle (project):
```gradle
allprojects {
    repositories {
        maven {
            url = URI("https://jitpack.io")
        }
    }
}
```
> Step 2: Add it to your build.gradle (app)

```gradle
dependencies {
     implementation("com.github.developwithishfaq:easy-fcm:1.2.1")
}
```

## Usage

**Create Instance**
- Easily Create Instance Of **EasyFcm** By Passing Your Server Key In Constructor.
```
    val easyFcm = EasyFcm(serverKey = YOUR_SERVER_KEY)
```
**Implementation**

If you want to test your client side code you can do that easily by our **pushTestNotification()** function
```
easyFcm.pushTestNotification(sendType = SendType.Topic("All"))
```
But for real scenarios we need to pass some data like title body or any other custom paramteres for that we do it like this:
```
easyFcm.pushNotification(
    params = FcmParams(
        remoteNotificationData = FcmNotification(
            title = NOTIFICATION_TITLE,
            message = NOTIFICATION_MESSAGE
        ),
    ),
    sendType = SendType.Topic("All")
)
```
Now we have option of params So that we can send data for Remote Message We Receive In Client Side. But There is something 
missing how can we send any extra params?
We can do that easily like:

```
easyFcm.pushNotification(
    params = FcmParams(
        remoteNotificationData = FcmNotification(
            title = NOTIFICATION_TITLE,
            message = NOTIFICATION_MESSAGE
        ),
        dataParams = listOf(
            Pair(KEY_1,KEY_1_VALUE),
            Pair(KEY_2,KEY_2_VALUE)
        )
    ),
    sendType = SendType.Topic("All")
)
```
Yes you got it perfectly we can pass extra arguments as much as we want through **dataParams**, we pass list of Pairs, each pair 
containg a key and data againts that key.
Now the questions is how we can get that **dataParams** on client side. That is also easy:
```
val valueOne = remoteMessage.data[KEY_1]
val valueTwo = remoteMessage.data[KEY_2]
```
So on client We have to create a class and have to extend it with **FirebaseMessagingService** then we need to ovveride two methods
> onMessageReceived
> onNewToken
But we need to declare our class inside Manifest like this:

```
<service android:name=".utils.FcmService"
    android:exported="true">
    <intent-filter>
        <action android:name="com.google.firebase.MESSAGING_EVENT" />
    </intent-filter>
</service>
```
Thanks



