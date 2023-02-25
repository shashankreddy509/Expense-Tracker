package com.techradicle.expensetracker.presentation.dashboard.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.copy
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.techradicle.expensetracker.core.vico.VicoUtils.lineChartColors
import com.techradicle.expensetracker.core.vico.VicoUtils.pointConnector
import com.techradicle.expensetracker.core.vico.rememberChartStyle
import com.techradicle.expensetracker.core.vico.rememberMarker
import com.techradicle.expensetracker.domain.model.ChartData
import java.time.LocalDate

@Composable
fun ChartView(chartData: List<ChartData>) {

    class Entry(
        val localDate: LocalDate,
        override val x: Float,
        override val y: Float,
    ) : ChartEntry {
        override fun withY(y: Float) = Entry(localDate, x, y)
    }

    val chartEntryModelProducer = chartData.mapIndexed { index, (string, y) ->
        Entry(
            LocalDate.parse(string),
            index.toFloat(),
            y.toFloat()
        )
    }.let { ChartEntryModelProducer(it) }

    val axisValueFormatter =
        AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, chartValues ->
            (chartValues.chartEntryModel.entries.first().getOrNull(value.toInt()) as Entry?)
                ?.localDate
                ?.run { "$dayOfMonth/$monthValue" }
                .orEmpty()
        }

    ProvideChartStyle(rememberChartStyle(chartColors = lineChartColors)) {
        val defaultLines = currentChartStyle.lineChart.lines
        val lineChart = lineChart(
            remember(defaultLines) {
                defaultLines.map { defaultLine -> defaultLine.copy(pointConnector = pointConnector) }
            },
        )
        Chart(
            chart = remember(lineChart) { lineChart },
            chartModelProducer = chartEntryModelProducer,
            startAxis = startAxis(guideline = null),
            bottomAxis = bottomAxis(valueFormatter = axisValueFormatter, guideline = null),
            marker = rememberMarker()
        )
    }

}