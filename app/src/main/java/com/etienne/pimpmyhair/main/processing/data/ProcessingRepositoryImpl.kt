package com.etienne.pimpmyhair.main.processing.data

import com.etienne.pimpmyhair.main.processing.domain.ProcessingRepository
import io.reactivex.rxjava3.core.Single

class ProcessingRepositoryImpl(private val api: ProcessingApi) : ProcessingRepository {
    override fun sendData(encodedImage: String): Single<String> =
        api.send(ProcessingDataFormEntity(encodedImage)).map { it.base64Output }
}
