package app.parvin.weatherappp.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import app.parvin.weatherappp.WeatherApplication
import app.parvin.weatherappp.domain.interactor.WeatherInteractor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver @Inject constructor() : BroadcastReceiver() {

    @Inject lateinit var interactor: WeatherInteractor
    @Inject lateinit var helper: NotificationHelper

    override fun onReceive(context: Context, intent: Intent?) {
        Toast.makeText(context, "ily", Toast.LENGTH_SHORT).show()
        runBlocking {
            helper.showNotification(interactor.getTomorrowTemperature())
        }
    }

}
