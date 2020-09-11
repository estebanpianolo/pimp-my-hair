package com.etienne.pimpmyhair.domain

import io.reactivex.rxjava3.core.Observable

interface ResultHistoryRepository {
    fun getHistory(): Observable<List<Result>>
}
