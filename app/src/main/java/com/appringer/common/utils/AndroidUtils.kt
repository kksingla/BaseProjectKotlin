package com.appringer.common.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.appringer.wedeliver.R
import com.appringer.wedeliver.activities.SplashActivity
import com.appringer.wedeliver.activities.WebActivity
import com.appringer.wedeliver.config.MyApplication
import com.appringer.wedeliver.constants.IntentConstant
import java.io.UnsupportedEncodingException
import java.net.URL
import java.net.URLDecoder
import java.util.*

object AndroidUtils {
    fun postNotification(context: Context, title: String?, desc: String?, url: String, imgUrl: String?) {
        // Create an explicit intent for an Activity in your app
        val intent = if (!TextUtils.isEmpty(url) && url.toUpperCase().startsWith("HTTP")) {
            try {
                Intent(context, WebActivity::class.java)
                        .putExtra(IntentConstant.URL, url)
            } catch (ex: Exception) {
                ex.printStackTrace()
                Intent(context, SplashActivity::class.java)
            }
        } else {
            Intent(context, SplashActivity::class.java)

        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        // use System.currentTimeMillis() to have a unique ID for the pending intent
        val pendingIntent = PendingIntent.getActivity(context, System.currentTimeMillis().toInt(), intent, 0)
        val mBuilder = NotificationCompat.Builder(context, context.getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_push_noti)
                .setContentTitle(title)
                .setContentText(desc)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        createNotificationChannel()
        var bitmap_image: Bitmap? = null
        try {
            val lUrl = URL(imgUrl)
            bitmap_image = BitmapFactory.decodeStream(lUrl.openConnection().getInputStream())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (bitmap_image != null) {
            val s = NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap_image)
            s.setSummaryText(desc)
            mBuilder.setStyle(s)
        }
        val notificationManager = NotificationManagerCompat.from(context)
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, mBuilder.build())
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: String = MyApplication.context().getString(R.string.app_name)
            val description: String = MyApplication.context().getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(name, name, importance)
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager: NotificationManager = MyApplication.context().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun shareText(context: Context?, msg: String?) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg)
        sendIntent.type = "text/plain"
        context?.startActivity(sendIntent)
    }

    fun openPlayStore(context: Context?) {
        val uri = Uri.parse("market://details?id=" + context?.packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

        try {
            context?.startActivity(goToMarket)
        } catch (var4: ActivityNotFoundException) {
            context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + context.packageName)))
        }
    }


    fun openURL(mContext: Context?, mUrl: String) {
        var url = mUrl
        try {
            if (!url.startsWith("http")) {
                url = "http://$url"
            }
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext?.startActivity(i)
        } catch (var3: java.lang.Exception) {
            var3.printStackTrace()
        }
    }

    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun getHashMap(query: String?): Map<String, String>? {
        val queryPairs: MutableMap<String, String> = LinkedHashMap()
        val pairs = query?.split("&")?.toTypedArray() ?: arrayOf()
        for (pair in pairs) {
            val idx = pair.indexOf("=")
            try {
                queryPairs[URLDecoder.decode(pair.substring(0, idx), "UTF-8")] = URLDecoder.decode(pair.substring(idx + 1), "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
        }
        return queryPairs
    }

    fun getColor(color: Int): Int {
        return ContextCompat.getColor(MyApplication.context(), color)
    }

    fun openDial(mContext: Context?, mno: String) {
        try {
            val i = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$mno"))
            mContext?.startActivity(i)
        } catch (var3: java.lang.Exception) {
            var3.printStackTrace()
        }
    }
}