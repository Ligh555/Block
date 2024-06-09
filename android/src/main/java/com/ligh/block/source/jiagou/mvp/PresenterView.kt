package com.ligh.block.source.jiagou.mvp

import android.widget.EditText
import com.ligh.block.source.jiagou.DataModel

/**
 * presenter 持有试图自行更新
 */
class PresenterView(private val editView: EditText) {

    fun updateView() {
        val model = DataModel()
        val text = model.getData()
        editView.setText(text)
    }
}