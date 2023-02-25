package com.techradicle.expensetracker.core.vico

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.core.chart.DefaultPointConnector
import com.patrykandpatrick.vico.core.component.shape.DashedShape
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.cornered.Corner
import com.patrykandpatrick.vico.core.component.shape.cornered.MarkerCorneredShape

object VicoUtils {

    const val LABEL_BACKGROUND_SHADOW_RADIUS = 4f
    const val LABEL_BACKGROUND_SHADOW_DY = 2f
    const val LABEL_LINE_COUNT = 1
    const val GUIDELINE_ALPHA = .2f
    const val INDICATOR_SIZE_DP = 36f
    const val INDICATOR_OUTER_COMPONENT_ALPHA = 32
    const val INDICATOR_CENTER_COMPONENT_SHADOW_RADIUS = 12f
    const val GUIDELINE_DASH_LENGTH_DP = 8f
    const val GUIDELINE_GAP_LENGTH_DP = 4f
    const val SHADOW_RADIUS_MULTIPLIER = 1.3f

    val labelBackgroundShape = MarkerCorneredShape(Corner.FullyRounded)
    val labelHorizontalPaddingValue = 8.dp
    val labelVerticalPaddingValue = 4.dp
    val labelPadding = dimensionsOf(labelHorizontalPaddingValue, labelVerticalPaddingValue)
    val indicatorInnerAndCenterComponentPaddingValue = 5.dp
    val indicatorCenterAndOuterComponentPaddingValue = 10.dp
    val guidelineThickness = 2.dp
    val guidelineShape = DashedShape(Shapes.pillShape, GUIDELINE_DASH_LENGTH_DP, GUIDELINE_GAP_LENGTH_DP)


    const val COLOR_1_CODE = 0xff916cda
    const val COLOR_2_CODE = 0xffd877d8
    const val COLOR_3_CODE = 0xfff094bb
    const val COLOR_4_CODE = 0xfffdc8c4

    val color1 = Color(COLOR_1_CODE)
    val color2 = Color(COLOR_2_CODE)
    val color3 = Color(COLOR_3_CODE)
    val color4 = Color(COLOR_4_CODE)
    val chartColors = listOf(color1)
    val lineChartColors = listOf(color4)
    const val COLUMN_WIDTH_DP = 16f
    val pointConnector = DefaultPointConnector(cubicStrength = 0f)

}