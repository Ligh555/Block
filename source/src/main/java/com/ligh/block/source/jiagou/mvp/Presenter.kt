package com.ligh.block.source.jiagou.mvp

import com.ligh.block.source.jiagou.DataModel

class Presenter(private val update: IUpdate) {

    fun updateView() {
        val model = DataModel()
        val text = model.getData()
        update.updateView(text)
    }

}

interface IUpdate{
    fun updateView(text :String)
}