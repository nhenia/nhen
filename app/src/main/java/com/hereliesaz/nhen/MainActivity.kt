package com.hereliesaz.nhen

import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import android.util.TypedValue

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set background blur radius if API level allows
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            window.setBackgroundBlurRadius(50) // Blur background behind the window
        }

        val imageView = findViewById<ImageView>(R.id.imageView)
        val phoneText = findViewById<TextView>(R.id.phoneText)
        val nhEnText = findViewById<TextView>(R.id.nhEnText)

        val width = resources.displayMetrics.widthPixels
        imageView.layoutParams.width = (width * 0.75).toInt()
        imageView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        imageView.requestLayout()

        imageView.setImageResource(R.drawable.qr_code)

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
    }
}
