package com.mobileapp.rpm.takenote.data.repository

import android.arch.lifecycle.MutableLiveData
import com.mobileapp.rpm.takenote.data.local.RoomData
import com.mobileapp.rpm.takenote.data.local.table.HistoryEntity
import com.mobileapp.rpm.takenote.model.History
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Copyright 2018 Renz Manacmol.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@Singleton
class Repository
@Inject constructor(private val roomData: RoomData) : IRepository {

    val allCompositeDisposable: MutableList<Disposable> = arrayListOf()

    override fun addHistory(history: History) {
        roomData.historyDao().insertHistory(HistoryEntity(0, history.note, history.timestamp))
    }

    override fun getHistory(): MutableLiveData<List<History>> {
        val mutableLiveData = MutableLiveData<List<History>>()
        val disposable = roomData.historyDao().selectAllHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ historyList ->
                    mutableLiveData.value = transform(historyList)
                }, { t: Throwable? -> t?.printStackTrace() })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }

    private fun transform(history: List<HistoryEntity>): ArrayList<History> {
        val historyList = ArrayList<History>()
        history.forEach {
            historyList.add(History(it.note, it.timestamp))
        }
        return historyList
    }

    override fun deleteAllHistory() {
        roomData.historyDao().deleteAll()
    }
}