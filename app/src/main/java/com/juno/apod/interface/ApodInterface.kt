package com.juno.apod.`interface`

interface ApodInterface {

    interface APodModel {
        fun getAPODbyDate(date: String, presenter: apodPresenter)
        fun getTitle(): String
        fun getImage(): String
        fun getHDImage(): String
        fun getDescription(): String
        fun getVideoUrl(): String
        fun getMediaType(): String

    }

    interface apodView {
        fun updateViewData()
    }

    interface apodPresenter {
        fun networkCall(date: String)
        fun showTitle(): String
        fun uiAutoUpdate()
        fun showImage(): String
        fun showHdImage(): String
        fun showDescription(): String
        fun showVideoUrl(): String
        fun showMediaType(): String
    }
}