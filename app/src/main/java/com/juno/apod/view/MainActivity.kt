package com.juno.apod.view

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.juno.apod.R
import com.juno.apod.`interface`.ApodInterface
import com.juno.apod.presenter.ApodPresenter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity(), ApodInterface.apodView {

    private var titleTV: TextView? = null
    private var descriptionTV: TextView? = null
    private var presenter: ApodPresenter? = null
    private var imageView: ImageView? = null
    private var fullScreenImageView: ImageView? = null
    private var datePicked: String? = null
    private var imageUrl: String? = null
    private var hDimageUrl: String? = null
    private var videoUrl: String? = null
    private var extractedId: String? = null
    private var mediaType: String? = null

    private var progressBar: ProgressBar? = null
    private var datePickImageView: ImageView? = null
    private var TAG = "MainActivity"
    private var cal = Calendar.getInstance()

    private val todayDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        initialize()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)


                updateDateInView()

            }

        datePick!!.setOnClickListener {
            DatePickerDialog(
                this@MainActivity,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        fullScreen!!.setOnClickListener {
            if (mediaType.equals("video")) {
                val intent = Intent(this@MainActivity, PlayVideoActivity::class.java)
                intent.putExtra("VIDEO_URL", extractedId)
                startActivity(intent)

            } else {
                val intent = Intent(this@MainActivity, FullScreenActivity::class.java)
                intent.putExtra("IMAGE_URL", imageUrl)
                startActivity(intent)

            }

        }
    }


    override fun updateViewData() {

        videoUrl = presenter?.showVideoUrl()
        mediaType = presenter?.showMediaType()

        extractedId = extractYTId(videoUrl)


        if (mediaType.equals("video")) {
            imageUrl = "https://img.youtube.com/vi/$extractedId/0.jpg"

            Picasso.get()
                .load(imageUrl)
                .into(image, object : Callback {
                    override fun onSuccess() {
                        Log.d(TAG, "success")
                        progressBar!!.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        Log.d(TAG, "error")
                        val toast = Toast.makeText(
                            applicationContext,
                            "NOT SUCCESSFULL",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                })

            Picasso.get()
                .load(R.drawable.play)
                .into(fullScreen, object : Callback {
                    override fun onSuccess() {
                        Log.d(TAG, "success")
                        progressBar!!.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        Log.d(TAG, "error")
                        val toast = Toast.makeText(
                            applicationContext,
                            "NOT SUCCESSFULL",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                })


        } else {
            imageUrl = presenter?.showImage()
            hDimageUrl = presenter?.showHdImage()

            Picasso.get()
                .load(presenter?.showImage())
                .into(image, object : Callback {
                    override fun onSuccess() {
                        Log.d(TAG, "success")
                        progressBar!!.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        Log.d(TAG, "error")
                        val toast = Toast.makeText(
                            applicationContext,
                            "NOT SUCCESSFULL",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                })

            Picasso.get()
                .load(R.drawable.expand)
                .into(fullScreen, object : Callback {
                    override fun onSuccess() {
                        Log.d(TAG, "success")
                        progressBar!!.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        Log.d(TAG, "error")
                        val toast = Toast.makeText(
                            applicationContext,
                            "NOT SUCCESSFULL",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                })

        }

        titleOfAPOD.text = presenter?.showTitle()
        description.text = presenter?.showDescription()
    }

    private fun extractYTId(ytUrl: String?): String? {
        var vId: String? = null
        val pattern = Pattern.compile(
            "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
            Pattern.CASE_INSENSITIVE
        )
        val matcher = pattern.matcher(ytUrl)
        if (matcher.matches()) {
            vId = matcher.group(1)
        }
        return vId
    }

    private fun initialize() {

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        presenter = ApodPresenter(this)
        titleTV = findViewById(R.id.titleOfAPOD)
        descriptionTV = findViewById(R.id.description)
        imageView = findViewById(R.id.image)
        fullScreenImageView = findViewById(R.id.fullScreen)
        datePickImageView = findViewById(R.id.datePick)
        progressBar = findViewById(R.id.my_progressBar)

        progressBar!!.visibility = View.VISIBLE

        presenter?.networkCall(todayDate)
    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        datePicked = sdf.format(cal.time)
        presenter?.networkCall(datePicked!!)
    }
}



