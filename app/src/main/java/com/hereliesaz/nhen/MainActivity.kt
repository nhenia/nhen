package com.hereliesaz.nhen

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var isQrVisible = true
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set background blur radius if API level allows
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            window.setBackgroundBlurRadius(100)
        }

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
                // Close the activity on any fling
                finish()
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

            // Blur the ImageView if the API level supports it
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val blurEffect = RenderEffect.createBlurEffect(50f, 50f, Shader.TileMode.MIRROR)
                imageView.setRenderEffect(blurEffect)
            }
            // Fade out the ImageView
            fade(imageView, 0f) {
                imageView.visibility = View.INVISIBLE
            }

            // Make text views visible and fade them in
            phoneText.visibility = View.VISIBLE
            nhEnText.visibility = View.VISIBLE
            fade(phoneText, 1f)
            fade(nhEnText, 1f)

        } else {
            // --- Transition from Text to QR code ---

            // "Disintegrate" the text views
            disintegrate(phoneText)
            disintegrate(nhEnText) {
                // This onEnd callback ensures the QR code appears only after text is gone
                imageView.visibility = View.VISIBLE
                // Remove the blur effect
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    imageView.setRenderEffect(null)
                }
                // Fade the ImageView back in
                fade(imageView, 1f)
            }
        }
        // Flip the state
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
                    onEnd?.invoke() // Execute callback when animation finishes
                }
            })
            start()
        }
    }

    /**
     * Creates a "disintegration" effect for a TextView by animating its properties
     * to random values before hiding it.
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
                    // Reset properties for the next time it becomes visible
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
