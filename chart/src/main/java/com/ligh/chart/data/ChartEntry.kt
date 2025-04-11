package com.ligh.chart.data

data class ChartEntry(
    val time: String,  // e.g. "09:30"
    val inflow: Float, // 单位：亿
    val index: Float   // 上证指数
)