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

package com.maina.ciaAgendi.ui.splash

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.maina.ciaAgendi.BuildConfig
import com.maina.ciaAgendi.R
import com.maina.ciaAgendi.di.ViewModelFactory
import com.maina.ciaAgendi.ui.home.HomeActivity
import com.maina.ciaAgendi.utils.getViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SplashViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_splash)

        versionLabel.text = "v ${BuildConfig.VERSION_NAME}"

        viewModel = getViewModel(this, viewModelFactory)
        viewModel.initialised.observe(this, Observer {
            if (it == true) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        })
    }
}
