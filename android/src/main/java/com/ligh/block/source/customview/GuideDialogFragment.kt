package com.ligh.block.source.customview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.ligh.block.source.R

class GuideDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 设置布局
        val view = inflater.inflate(R.layout.guide_dialog_fragment, container, false).apply {
            setOnClickListener {
                dismiss()
            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        // 设置 Dialog 全屏
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}
