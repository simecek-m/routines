package dev.simecek.routines.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.simecek.routines.databinding.ActivityMainBinding
import dev.simecek.routines.settings.SettingsManager
import dev.simecek.routines.utils.managers.NotificationManager
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.layout.viewTreeObserver.addOnPreDrawListener(object: ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                var preferences: Preferences? = null
                lifecycleScope.launch {
                    preferences = settingsManager.cache()
                }
                return if(preferences != null) {
                    binding.layout.viewTreeObserver.removeOnPreDrawListener(this)
                    true
                } else {
                    false
                }
            }
        })
        setContentView(binding.root)
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