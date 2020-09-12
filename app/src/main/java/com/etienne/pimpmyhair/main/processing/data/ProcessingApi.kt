package com.etienne.pimpmyhair.main.processing.data

import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ProcessingApi {

    @POST("upload")
    fun send(@Body form: ProcessingDataFormEntity): Single<ProcessingDataEntity>
}
