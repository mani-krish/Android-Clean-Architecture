package com.assessment.cleanarchitecture

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.assessment.cleanarchitecture.presentation.fragment.ListCountryFragment
import com.assessment.cleanarchitecture.utils.showDialogExit
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        keepSplashScreenFor5Seconds()
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListCountryFragment.newInstance()).commitNow()
        }

    }

    override fun onBackPressed() {
        showDialogExit()
    }

    /* Keep splash screen on-screen for longer period. This is useful if you need to load data when splash screen is appearing */
    private fun keepSplashScreenFor5Seconds() {
        val content = findViewById<View>(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                Thread.sleep(1000)
                content.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })
    }
}