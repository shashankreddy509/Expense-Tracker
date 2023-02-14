package com.techradicle.expensetracker.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@ExperimentalMaterial3Api
@Composable
fun TextFieldWithDatePicker(
    text: String,
    value: MutableState<String>
) {
    val context = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            value.value = "$mYear-${mMonth + 1}-$mDayOfMonth"
        }, mYear, mMonth, mDay
    )

    Row(modifier = Modifier.padding(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 8.dp)) {
        Text(
            text = text,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .weight(0.3f),
            fontSize = 21.sp,
            color = Color.Black
        )
        TextField(
            modifier = Modifier
                .weight(0.7f)
                .clickable {
                    datePickerDialog.show()
                },
            value = value.value,
            onValueChange = {
                value.value = it
            },
            maxLines = 1
        )
//        Box(
//            modifier = Modifier
//                .matchParentSize()
//                .alpha(0f)
//                .clickable(onClick = onClick),
//        )
    }
}