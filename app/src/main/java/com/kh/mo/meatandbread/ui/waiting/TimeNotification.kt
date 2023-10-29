package com.kh.mo.meatandbread.ui.waiting

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.kh.mo.meatandbread.MainActivity
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.util.Constants.CHANNEL_ID

fun createNotification(context: Context): NotificationCompat.Builder {

    val notificationManager =
        context.getSystemService(NotificationManager::class.java) as NotificationManager

    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context, 0, intent, PendingIntent.FLAG_IMMUTABLE,
    )

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelName = context.getString(R.string.channelName)
        val channelImportance = NotificationManager.IMPORTANCE_DEFAULT
        val notificationChannel = NotificationChannel(CHANNEL_ID, channelName, channelImportance)
        notificationManager.createNotificationChannel(notificationChannel)
    }

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.time)
        .setContentTitle("Remaining Time")
        .setContentText("The meal is prepared for you")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)
        .setContentIntent(pendingIntent)

    notificationManager.notify(1, builder.build())

    return builder
}

