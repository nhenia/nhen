package com.hereliesaz.nhen

import android.content.Context
import android.os.*
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply heavy window blur (API 31+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            window.setBackgroundBlurRadius(100)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vibration
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createWaveform(longArrayOf(0, 60, 40, 80, 20, 120), -1)
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(200)
        }

        // Swipe-to-dismiss
        val card = findViewById<CardView>(R.id.blur_card)
        val gesture = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (velocityY > 1000) {
                    card.animate()
                        .alpha(0f)
                        .translationY(300f)
                        .setDuration(300)
                        .withEndAction { finish() }
                        .start()
                    return true
                }
                return false
            }
        })

        card.setOnTouchListener { _, event -> gesture.onTouchEvent(event) }
    }

    override fun onResume() {
        super.onResume()
        // Auto-dismiss after 5 seconds
        window.decorView.postDelayed({
            finish()
        }, 5000)
    }
}
