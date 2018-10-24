/*
 * Copyright (c) 2018. Tony Maina.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.maina.ciaAgendi.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.button.MaterialButton
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maina.ciaAgendi.R
import com.maina.ciaAgendi.ui.base.RoundedBottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_picker.*

class PickerFragment : RoundedBottomSheetDialogFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_picker, container, false)
    }

    override fun onViewCreated(contentView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(contentView, savedInstanceState)

        btnClick0.setOnClickListener(this)
        btnClick1.setOnClickListener(this)
        btnClick2.setOnClickListener(this)
        btnClick3.setOnClickListener(this)
        btnClick4.setOnClickListener(this)
        btnClick5.setOnClickListener(this)
        btnClick6.setOnClickListener(this)
        btnClick7.setOnClickListener(this)
        btnClick8.setOnClickListener(this)
        btnClick9.setOnClickListener(this)
        btnClickGo.setOnClickListener {
            callback?.invoke(label.text.toString().toInt())
            dismiss()
        }
        btnBackSpace.setOnClickListener {
            val currText = label.text
            if (!currText.isNullOrEmpty()) {
                label.text = currText.dropLast(1)
            }

            if (TextUtils.isEmpty(label.text)) {
                btnClickGo.visibility = View.INVISIBLE
            }
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onClick(view: View?) {
        if (view is MaterialButton) {
            label.text = "${label.text}${view.text}".toInt().toString()

            if (label.text == "0") return

            btnClickGo.visibility = if (TextUtils.isEmpty(label.text)) View.INVISIBLE else View.VISIBLE
        }
    }

    companion object {
        private var callback: ((Int) -> Unit)? = null

        fun newInstance(callback: (Int) -> Unit): PickerFragment {
            val fragment = PickerFragment()
            this.callback = callback

            return fragment
        }
    }
}