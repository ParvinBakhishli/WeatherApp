package app.parvin.weatherappp.util

import android.app.AlarmManager
import android.app.AlarmManager.OnAlarmListener
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.core.graphics.drawable.toBitmap
import app.parvin.weatherappp.R
import app.parvin.weatherappp.domain.interactor.WeatherInteractor
import app.parvin.weatherappp.presentation.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created on 10.10.24 at 18:08
 *
 * @author Parvin Bakhishli
 */

@Singleton
class NotificationHelper @Inject constructor(
    private val interactor: WeatherInteractor,
    @ApplicationContext private val context: Context,
) {

    private val notificationManager = context.getSystemService<NotificationManager>()
    private val alarmManager = context.getSystemService<AlarmManager>()


    fun initialize() {
        setNotificationTime()
    }

    private fun setNotificationTime() {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 13)
        calendar.set(Calendar.MINUTE, 22)
        calendar.set(Calendar.SECOND, 0)

        alarmManager?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.time.time,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    suspend fun showNotification(temp: Pair<Int, Int>) {
        val weatherIntent = Intent(context, MainActivity::class.java)
        val pendingWeatherIntent = PendingIntent.getActivity(
            context,
            1,
            weatherIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val d = context.getDrawable(interactor.getTomorrowWeatherCode())?.toBitmap()

        val notification = NotificationCompat.Builder(context, TOMORROW_WEATHER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_cloudy)
            .setContentTitle("Weather forecast for tomorrow")
            .setContentText("${temp.first}°C and ${temp.second}°C")
            .setLargeIcon(d)
            .setContentIntent(pendingWeatherIntent)
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
            .build()

        notificationManager?.notify(1, notification)
    }

    companion object {
        const val TOMORROW_WEATHER_CHANNEL_ID = "tomorrow_weather_channel"
    }
}