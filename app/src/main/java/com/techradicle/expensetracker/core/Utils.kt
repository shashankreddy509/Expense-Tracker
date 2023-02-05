package com.techradicle.expensetracker.core

import android.util.Log
import androidx.paging.LoadState
import com.techradicle.expensetracker.core.AppConstants.TAG

class Utils {

    companion object {
        fun print(e: Exception?) {
            Log.e(TAG, e?.message ?: e.toString())
        }

        fun print(errorState: LoadState.Error) {
            val error = errorState.error
            Log.d(TAG, error.message ?: error.toString())
        }
    }
}