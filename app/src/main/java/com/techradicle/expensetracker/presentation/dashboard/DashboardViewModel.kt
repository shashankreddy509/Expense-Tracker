package com.techradicle.expensetracker.presentation.dashboard

import androidx.lifecycle.ViewModel
import com.techradicle.expensetracker.domain.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: DashboardRepository
) : ViewModel() {
    val user = repo.user
}
