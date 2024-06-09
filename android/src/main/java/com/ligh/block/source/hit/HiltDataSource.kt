package com.ligh.block.source.hit

import javax.inject.Singleton

@Singleton
class HiltDataSource {
    fun requestId(): String = "1"
    fun requestName(): String = "name"
}