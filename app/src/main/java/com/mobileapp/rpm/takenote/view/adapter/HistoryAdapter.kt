package com.mobileapp.rpm.takenote.view.adapter

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

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mobileapp.rpm.takenote.R
import com.mobileapp.rpm.takenote.databinding.ItemRowHistoryBinding
import com.mobileapp.rpm.takenote.model.History
import java.util.*

class HistoryAdapter(historyList: ArrayList<History>) : RecyclerView.Adapter<HistoryAdapter.HViewHolder>() {

    private val history: MutableList<History>

    init {
        this.history = ArrayList()
        this.history.addAll(historyList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding
        binding = DataBindingUtil.inflate(inflater, R.layout.item_row_history, parent, false)
        return HViewHolder(binding as ItemRowHistoryBinding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.HViewHolder, position: Int) {
        holder.bindItem(history[position])
        val history = history[position]
        holder.mBinding?.tvNote?.text = history.note
        holder.mBinding?.tvTimestamp?.text = history.timestamp
    }

    override fun getItemCount(): Int {
        return history.size
    }

    inner class HViewHolder internal constructor(binding: ItemRowHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        var mBinding: ItemRowHistoryBinding? = binding

        internal fun bindItem(history: History) {
            mBinding?.model = history
            mBinding?.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
