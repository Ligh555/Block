package com.ligh.block.source.hit

import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


class UserHilt @Inject constructor(){
    @Inject
    lateinit var hiltTest :HiltTest
    fun test(){}
}
class HiltTest @Inject constructor() {
    @Inject
     lateinit var test: Test
}

@ActivityScoped
class Test @Inject constructor() {
    var value: String = ""
}


class HiltTes1 @Inject constructor() {
    @Inject
    lateinit var test: Test
}