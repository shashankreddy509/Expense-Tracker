package com.techradicle.expensetracker.core

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.paging.LoadState
import com.techradicle.expensetracker.core.AppConstants.ACCOUNTS
import com.techradicle.expensetracker.core.AppConstants.ADD_RECEIPT
import com.techradicle.expensetracker.core.AppConstants.HOME
import com.techradicle.expensetracker.core.AppConstants.SIGN_OUT
import com.techradicle.expensetracker.core.AppConstants.TAG
import com.techradicle.expensetracker.domain.model.ChartData
import com.techradicle.expensetracker.domain.model.DrawerItem
import com.techradicle.expensetracker.domain.model.ReceiptData
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class Utils {

    companion object {

        val month = LocalDate.now().month
        val year = LocalDate.now().year
        val date = LocalDate.of(year, month, 1)

        fun print(e: Exception?) {
            Log.e(TAG, e?.message ?: e.toString())
        }

        fun print(errorState: LoadState.Error) {
            val error = errorState.error
            Log.d(TAG, error.message ?: error.toString())
        }

        val items = listOf(
            DrawerItem(Icons.Default.Home, HOME, HOME),
            DrawerItem(Icons.Default.AddCircle, ADD_RECEIPT, ADD_RECEIPT),
            DrawerItem(Icons.Default.AccountCircle, ACCOUNTS, ACCOUNTS),
            DrawerItem(Icons.Default.ExitToApp, SIGN_OUT, SIGN_OUT)
        )

        fun dateFormatter(createdAt: Date): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
            return simpleDateFormat.format(createdAt)
        }

        fun getCorrectDate(date: String, createdAt: Date): String {
            val split = date.split("-")
            val receiptOcrDate = Calendar.getInstance()
            receiptOcrDate.set(
                split[0].toInt(),
                (split[1].toInt() - 1),
                split[2].toInt()
            )
            return if (receiptOcrDate.time.before(createdAt)) {
                date
            } else {
                dateFormatter(createdAt)
            }
        }

        fun getLastDateOfTheMonth() = date.plusMonths(1).minusDays(1).toString()

        fun getFirstDateOfTheMonth() = date.toString()

        fun getTotal(items: List<ReceiptData>): String {
            return String.format("%.2f", items.sumOf { it.total!! })
//            return items.sumOf { it.total!! }
        }

        fun getChartData(items: List<ReceiptData>): List<ChartData> {
            val chartData = mutableListOf<ChartData>()
            var tempLst = mutableListOf<ReceiptData>()
            var amount = 0.0
            var date = ""
            for (item in items) {
                if (date.isEmpty()) date = item.date!!
                if (date.equals(item.date, false)) {
                    amount += item.total!!
                    tempLst.add(item)
                } else {
                    amount = String.format("%.2f", amount).toDouble()
                    chartData.add(ChartData(date, amount, tempLst))
                    date = item.date!!
                    amount = item.total!!
                    tempLst = mutableListOf()
                    tempLst.add(item)
                }
            }
            amount = String.format("%.2f", amount).toDouble()
            chartData.add(ChartData(date, amount, tempLst))
            return chartData.sortedBy { it.date }
        }
    }
}