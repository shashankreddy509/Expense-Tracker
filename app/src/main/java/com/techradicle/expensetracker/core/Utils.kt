package com.techradicle.expensetracker.core

import android.util.Log
import androidx.paging.LoadState
import com.techradicle.expensetracker.core.AppConstants.TAG
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object {
        fun print(e: Exception?) {
            Log.e(TAG, e?.message ?: e.toString())
        }

        fun print(errorState: LoadState.Error) {
            val error = errorState.error
            Log.d(TAG, error.message ?: error.toString())
        }

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
    }
}