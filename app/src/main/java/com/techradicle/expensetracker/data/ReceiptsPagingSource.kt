package com.techradicle.expensetracker.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.techradicle.expensetracker.domain.model.ReceiptData
import kotlinx.coroutines.tasks.await

class ReceiptsPagingSource(
    private val query: Query
) : PagingSource<QuerySnapshot, ReceiptData>() {

    override fun getRefreshKey(state: PagingState<QuerySnapshot, ReceiptData>): QuerySnapshot? =
        null

    override suspend fun load(params: LoadParams<QuerySnapshot>) = try {
        val currentPage = params.key ?: query.get().await()
        val lastVisibleDocument = currentPage.documents[currentPage.size() - 1]
        val nextPage = query.startAfter(lastVisibleDocument).get().await()
        LoadResult.Page(
            data = currentPage.toObjects(ReceiptData::class.java).sortedByDescending { it.date },
            prevKey = null,
            nextKey = nextPage
        )
    } catch (e: Exception) {
        LoadResult.Error(e)
    }
}