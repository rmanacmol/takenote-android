package com.mobileapp.rpm.takenote.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.mobileapp.rpm.takenote.R
import com.mobileapp.rpm.takenote.model.History
import com.mobileapp.rpm.takenote.view.adapter.HistoryAdapter
import com.mobileapp.rpm.takenote.viewmodel.HistoryViewModel
import kotlinx.android.synthetic.main.activity_history.*

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

class HistoryActivity : AppCompatActivity() {

    private var viewModel: HistoryViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        viewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)
        btnClose.setOnClickListener {
            finish()
        }
        btnClear.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.string_clear))
                    .setPositiveButton(getString(R.string.string_yes)) { _, _ ->
                        viewModel?.clearHistory()
                        rv.adapter?.notifyDataSetChanged()
                    }
                    .setNegativeButton(getString(R.string.string_no)) { _, _ -> }
            builder.show()
        }
        populateHistory()
    }

    fun populateHistory() {
        viewModel?.getHistory()?.observe(this,
                Observer { it ->
                    if (it != null) {
                        if (it.isEmpty()) {
                            emptyView.visibility = View.VISIBLE
                            rv.visibility = View.GONE
                        } else {
                            emptyView.visibility = View.GONE
                            rv.visibility = View.VISIBLE
                        }
                        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
                        rv.adapter = HistoryAdapter(it as ArrayList<History>)
                    }
                })
    }
}