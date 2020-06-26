package com.juno.apod.presenter

import com.juno.apod.`interface`.ApodInterface
import com.juno.apod.model.repos.apodRepos


class ApodPresenter(apodView: ApodInterface.apodView) : ApodInterface.apodPresenter {


    private var view: ApodInterface.apodView = apodView
    private var model: ApodInterface.APodModel = apodRepos()


    override fun uiAutoUpdate() {
        view.updateViewData()
    }

    override fun showImage() = model.getImage()
    override fun showHdImage() = model.getHDImage()
    override fun showDescription() = model.getDescription()
    override fun showVideoUrl() = model.getVideoUrl()
    override fun showMediaType() = model.getMediaType()

    override fun networkCall(date: String) {
        model.getAPODbyDate(date, this)
    }

    override fun showTitle() = model.getTitle()


}