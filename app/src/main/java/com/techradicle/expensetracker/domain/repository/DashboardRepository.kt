package com.techradicle.expensetracker.domain.repository

import com.techradicle.expensetracker.domain.model.User

interface DashboardRepository {
    val user: User
}