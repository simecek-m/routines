package dev.simecek.routines.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import dev.simecek.routines.R
import dev.simecek.routines.utils.managers.NotificationManager
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        setBlackStatusAndNavigationBars()
        notificationManager.createNotificationChannels()
    }

    private fun setBlackStatusAndNavigationBars() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            window.statusBarColor = Color.BLACK
        }

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O_MR1) {
            window.navigationBarColor = Color.BLACK
        }
    }
}