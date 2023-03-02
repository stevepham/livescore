package com.ht117.livescore.receiver

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ht117.data.model.Match
import com.ht117.livescore.R

class MatchNotifyReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.run {
            val match = intent?.extras?.getParcelable<Match>("match")
            if (match != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(CHANNEL_ID, NAME, NotificationManager.IMPORTANCE_DEFAULT)
                    val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notifManager.createNotificationChannel(channel)
                }

                val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_team)
                    .setContentTitle(getString(R.string.match_coming))
                    .setContentText("${match.home} vs ${match.away} now")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .build()
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                with(NotificationManagerCompat.from(this)) {
                    notify(NOTIFICATION_ID, notification)
                }
            }
        }
    }

    companion object {
        private const val NAME = "name"
        private const val CHANNEL_ID = "123123"
        private const val NOTIFICATION_ID = 123
    }
}
