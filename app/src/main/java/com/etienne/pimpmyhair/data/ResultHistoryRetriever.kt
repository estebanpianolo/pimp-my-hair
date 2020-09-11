package com.etienne.pimpmyhair.data

import com.etienne.pimpmyhair.domain.Result
import com.etienne.pimpmyhair.domain.ResultHistoryRepository
import io.reactivex.rxjava3.core.Observable

class ResultHistoryRetriever : ResultHistoryRepository {
    override fun getHistory(): Observable<List<Result>> = Observable.just(listOf())
}
