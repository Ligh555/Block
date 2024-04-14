package com.ligh.block.source.jiagou.mvc

import com.ligh.block.source.jiagou.DataModel

class Controller {

    fun getContent(): String {
        // 获取model层的数据
        val model = DataModel()
        return model.getData()
    }
}