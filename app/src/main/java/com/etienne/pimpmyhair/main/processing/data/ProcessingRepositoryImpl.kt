package com.etienne.pimpmyhair.main.processing.data

import com.etienne.pimpmyhair.main.processing.domain.ProcessingRepository
import io.reactivex.rxjava3.core.Single

class ProcessingRepositoryImpl : ProcessingRepository {
    override fun sendData(encodedImage: String): Single<String> {
        return Single.just("")
    }
}
