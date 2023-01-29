package com.techradicle.expensetracker.data

import com.google.firebase.auth.FirebaseAuth
import com.techradicle.expensetracker.core.AppConstants.NO_VALUE
import com.techradicle.expensetracker.domain.model.User
import com.techradicle.expensetracker.domain.repository.DashboardRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepositoryImpl @Inject constructor(
    val auth: FirebaseAuth
) : DashboardRepository {
    override val user = User(
        uid = auth.currentUser?.uid ?: NO_VALUE,
        photoUrl = auth.currentUser?.photoUrl.toString(),
        displayName = auth.currentUser?.displayName ?: NO_VALUE,
        email = auth.currentUser?.email ?: NO_VALUE
    )
}