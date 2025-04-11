package com.ligh.chart

import com.ligh.base.activity.BaseActivity
import com.ligh.base.activity.binding
import com.ligh.chart.data.ChartEntry
import com.ligh.chart.databinding.ActivityChartBinding


class ChartActivity : BaseActivity() {
    override val viewBinding: ActivityChartBinding by binding()
    override fun initViewBinding() {
        val chartData = listOf(
            ChartEntry("09:30", 10f, 3200f),
            ChartEntry("10:00", 80f, 3210f),
            ChartEntry("10:30", 150f, 3230f),
            ChartEntry("11:00", 100f, 3220f),
            ChartEntry("13:00", 90f, 3215f),
            ChartEntry("14:00", 60f, 3205f),
            ChartEntry("15:00", 30f, 3200f)
        )
        viewBinding.chartView.setChartData(chartData)

    }

}