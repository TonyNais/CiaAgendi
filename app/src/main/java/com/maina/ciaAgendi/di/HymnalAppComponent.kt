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

package com.maina.ciaAgendi.di

import com.maina.ciaAgendi.HymnalApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(HymnalAppModule::class),
    (AndroidInjectionModule::class),
    (AndroidSupportInjectionModule::class),
    (ViewModelBuilder::class),
    (ActivityBuilder::class)])
interface HymnalAppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: HymnalApp): Builder

        fun build(): HymnalAppComponent
    }

    fun inject(app: HymnalApp)
}