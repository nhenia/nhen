package com.hereliesaz.nhen

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.*
import android.os.Bundle
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var isQrVisible = true
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rootLayout = findViewById<LinearLayout>(R.id.root_layout)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val phoneText = findViewById<TextView>(R.id.phoneText)
        val nhEnText = findViewById<TextView>(R.id.nhEnText)

        // Explicitly hide the text views on creation to ensure they don't appear initially.
        phoneText.visibility = View.GONE
        nhEnText.visibility = View.GONE

        // Set image dimensions
        val width = resources.displayMetrics.widthPixels
        val imageSize = (width * 0.75).toInt()
        imageView.layoutParams.width = imageSize
        imageView.layoutParams.height = imageSize
        imageView.requestLayout()

        imageView.setImageResource(R.drawable.qr_code)

        // Set custom font
        val troublemakerFont = ResourcesCompat.getFont(this, R.font.troublemarker_font)

        phoneText.apply {
            text = getString(R.string.phone)
            typeface = troublemakerFont
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
        }

        nhEnText.apply {
            text = getString(R.string.nh_en)
            typeface = troublemakerFont
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 60f)
        }

        // Setup gesture detection for both single taps and flings
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                toggleViews(imageView, phoneText, nhEnText)
                return true
            }

            override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                val rootLayout = findViewById<View>(R.id.root_layout)
                val screenWidth = resources.displayMetrics.widthPixels.toFloat()
                val screenHeight = resources.displayMetrics.heightPixels.toFloat()

                // Determine the primary axis of the fling
                val endX = if (abs(velocityX) > abs(velocityY)) {
                    // Horizontal fling
                    if (velocityX > 0) screenWidth else -screenWidth
                } else {
                    0f
                }

                val endY = if (abs(velocityY) >= abs(velocityX)) {
                    // Vertical fling
                    if (velocityY > 0) screenHeight else -screenHeight
                } else {
                    0f
                }

                rootLayout.animate()
                    .translationX(endX)
                    .translationY(endY)
                    .alpha(0f)
                    .setDuration(250) // A quick exit
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            finish()
                            // Disable the default activity transition animation to keep it clean
                            overridePendingTransition(0, 0)
                        }
                    })
                    .start()
                return true
            }
        })

        // Set the touch listener on the root layout to capture all touch events
        rootLayout.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true // Consume the event
        }
    }

    /**
     * Toggles the visibility and animation between the QR code and the text views.
     */
    private fun toggleViews(imageView: ImageView, phoneText: TextView, nhEnText: TextView) {
        if (isQrVisible) {
            // --- Transition from QR code to Text ---
            fade(imageView, 0f) {
                imageView.visibility = View.INVISIBLE
            }

            phoneText.visibility = View.VISIBLE
            nhEnText.visibility = View.VISIBLE
            fade(phoneText, 1f)
            fade(nhEnText, 1f)

        } else {
            // --- Transition from Text to QR code ---
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("phone number", phoneText.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Phone number copied", Toast.LENGTH_SHORT).show()

            disintegrate(phoneText)
            disintegrate(nhEnText) {
                imageView.visibility = View.VISIBLE
                fade(imageView, 1f)
            }
        }
        isQrVisible = !isQrVisible
    }

    /**
     * Animates the alpha of a view.
     */
    private fun fade(view: View, toAlpha: Float, onEnd: (() -> Unit)? = null) {
        ObjectAnimator.ofFloat(view, "alpha", view.alpha, toAlpha).apply {
            duration = 500 // Animation duration in milliseconds
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    onEnd?.invoke()
                }
            })
            start()
        }
    }

    /**
     * Creates a "disintegration" effect for a TextView.
     */
    private fun disintegrate(view: TextView, onEnd: (() -> Unit)? = null) {
        view.animate()
            .alpha(0f)
            .translationYBy(Random.nextInt(-200, 200).toFloat())
            .translationXBy(Random.nextInt(-200, 200).toFloat())
            .rotation(Random.nextInt(-360, 360).toFloat())
            .setDuration(700)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.GONE
                    // Reset properties
                    view.alpha = 1f
                    view.translationX = 0f
                    view.translationY = 0f
                    view.rotation = 0f
                    onEnd?.invoke()
                }
            })
            .start()
    }
}