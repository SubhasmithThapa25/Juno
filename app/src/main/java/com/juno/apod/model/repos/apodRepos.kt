package com.juno.apod.model.repos

import com.juno.apod.model.api.APodServices
import android.util.Log

import com.juno.apod.`interface`.ApodInterface
import com.juno.apod.model.api.ApodAPI
import com.juno.apod.model.modelClass.APOD
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class apodRepos : ApodInterface.APodModel {
    private var title = ""
    private var image = ""
    private var hdImage = ""
    private var description = ""
    private var videoUrl = ""
    private var mediaType = ""


    override fun getTitle() = title
    override fun getImage() = image
    override fun getHDImage() = hdImage
    override fun getDescription() = description
    override fun getVideoUrl() = videoUrl
    override fun getMediaType() = mediaType

    private var apiclient: APodServices? = null

    init {
        apiclient = ApodAPI.client.create(APodServices::class.java)
    }

    override fun getAPODbyDate(date: String, presenter: ApodInterface.apodPresenter) {
        val call = apiclient?.getAPod(date)

        call?.enqueue(object : Callback<APOD> {
            override fun onFailure(call: Call<APOD>?, t: Throwable?) {
                Log.d("failure", t.toString())
            }

            override fun onResponse(call: Call<APOD>?, response: Response<APOD>?) {
                if (response?.isSuccessful!!) {
                    val results = response.body()?.title
                    var imageResult = response.body()?.url
                    val hdImageResult = response.body()?.hdurl
                    val descriptionResult = response.body()?.explanation
                    val mediaTypeResult = response.body()?.media_type
                    if (mediaTypeResult.equals("image")) {
                        imageResult = response.body()?.url
                    } else {
                        videoUrl = response.body()?.url.toString()
                    }

                    title = results.toString()
                    image = imageResult.toString()
                    description = descriptionResult.toString()
                    hdImage = hdImageResult.toString()
                    mediaType = mediaTypeResult.toString()
                    presenter.uiAutoUpdate()

                }


            }


        })
    }


}