package com.example.contestalert

import android.animation.AnimatorSet
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.animation.AnimationSet
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splashscreen_icon.alpha = 0f
        splashscreen_icon
            .animate()
            .setDuration(2000)
            .alpha(1f)
            .withEndAction {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_exit)
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
    }
}