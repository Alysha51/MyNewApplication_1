package com.example.adminapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.Random
import java.util.concurrent.TimeUnit

class MyFirbaseMessagingService : FirebaseMessagingService(){
    private val ADMIN_CHANNEL_ID = "admin_channel"
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("fcm", "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("alisha", "Message data payload: ${remoteMessage.data}")

            // Check if data needs to be processed by long running job

        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d("naveed", "Message Notification Body: ${it.body}")
        }

        val notification_time = ModelPreferencesManager.getInt("notification_time")
        val showNotification = shouldShowNotification(notification_time)
        if (showNotification) {
            ModelPreferencesManager. saveLong("lastnotificationtimestamp", System.currentTimeMillis() )


            // Also if you intend on generating your own notifications as a result of a received FCM
            // message, here is where that should be initiated. See sendNotification method below.
            val intent = Intent(this, MainActivity::class.java)
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationID = Random().nextInt(3000)

            /*
        Apps targeting SDK 26 or above (Android O) must implement notification channels and add its notifications
        to at least one of them.
      */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setupChannels(notificationManager)
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT
            )

            val largeIcon = BitmapFactory.decodeResource(
                resources,
                R.drawable.ic_delete
            )

            val notificationSoundUri =
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_delete)
                .setLargeIcon(largeIcon)
                .setContentTitle(remoteMessage?.data?.get("title"))
                .setContentText(remoteMessage?.data?.get("content"))
                .setAutoCancel(true)
                .setSound(notificationSoundUri)
                .setContentIntent(pendingIntent)

            //Set notification color to match your app color template
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notificationBuilder.color = resources.getColor(R.color.white)
            }
            notificationManager.notify(notificationID, notificationBuilder.build())
        }
    }
    private fun shouldShowNotification(notificationTime: Int): Boolean  {
        val lastnotificationtime = ModelPreferencesManager. getLong("lastnotificationtimestamp")
        if (notificationTime==0 || lastnotificationtime==0L )

            return true
        else if (notificationTime==6){
            val currenttime = System.currentTimeMillis()- lastnotificationtime
            val hours=TimeUnit.MILLISECONDS.toHours(currenttime)
            if (hours>=6)
                return true
        }
        else if (notificationTime==24){
            val currenttime = System.currentTimeMillis()- lastnotificationtime
            val hours=TimeUnit.MILLISECONDS.toHours(currenttime)
            if (hours>=24)
                return true
        }
        else if (notificationTime==168){
            val currenttime = System.currentTimeMillis()- lastnotificationtime
            val hours=TimeUnit.MILLISECONDS.toHours(currenttime)
            if (hours>=168)
                return true
        }
        return false
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels(notificationManager: NotificationManager?) {
        val adminChannelName = "New notification"
        val adminChannelDescription = "Device to devie notification"

        val adminChannel: NotificationChannel
        adminChannel = NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH)
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        notificationManager?.createNotificationChannel(adminChannel)
    }
}