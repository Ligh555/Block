package com.ligh.block.source.hit

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class HiltRepositories @Inject constructor(
    private val dataSource: HiltDataSource
) {
    fun getUser() = dataSource.requestId() + dataSource.requestName()
}