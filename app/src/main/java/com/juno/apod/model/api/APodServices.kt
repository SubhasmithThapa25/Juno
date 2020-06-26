package com.juno.apod.model.api
import com.juno.apod.`interface`.Constant
import com.juno.apod.model.modelClass.APOD
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APodServices {

    @GET(Constant.APOD)
    fun getAPod(@Query("date") datePicked:String): Call<APOD>
}