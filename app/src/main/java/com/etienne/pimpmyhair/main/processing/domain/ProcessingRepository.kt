package com.etienne.pimpmyhair.main.processing.domain

import io.reactivex.rxjava3.core.Single

interface ProcessingRepository {
    fun sendData(encodedImage: String): Single<String>
}
