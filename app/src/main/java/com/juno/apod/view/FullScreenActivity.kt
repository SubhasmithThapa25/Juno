package com.juno.apod.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.juno.apod.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_full_screen.*

class FullScreenActivity : AppCompatActivity() {

    private var fullScreenImageView: ImageView? = null
    private var closeImageView: ImageView? = null
    private var progressBar: ProgressBar? = null


    private var TAG = "FullScreenActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)


        val imageUrl = intent.getStringExtra("IMAGE_URL")


        initialize()
        Picasso.get()
            .load(imageUrl)
            .into(fullscreen_content, object : Callback {
                override fun onSuccess() {
                    Log.d(TAG, "success")
                    progressBar!!.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    Log.d(TAG, "error")
                }
            })

        close!!.setOnClickListener {
            finish()
        }
    }

    private fun initialize() {

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        fullScreenImageView = findViewById(R.id.fullscreen_content)
        closeImageView = findViewById(R.id.close)

        progressBar = findViewById(R.id.my_progressBar)

        progressBar!!.visibility = View.VISIBLE


    }
}