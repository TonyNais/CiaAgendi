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

package com.maina.ciaAgendi.utils.prefs

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class HymnalPrefsImpl constructor(val context: Context) : HymnalPrefs {

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun getLanguage(): String = prefs.getString(PREF_LANGUAGE, "eng") as String

    override fun setLanguage(language: String) {
        prefs.edit()
                .putString(PREF_LANGUAGE, language)
                .apply()
    }

    override fun getLastHymnNumber(): Int = prefs.getInt(PREF_LAST_NUMBER, 0)

    override fun setLastHymnNumber(number: Int) {
        prefs.edit()
                .putInt(PREF_LAST_NUMBER, number)
                .apply()
    }

    override fun isNightMode(): Boolean = prefs.getBoolean(PREF_NIGHT_MODE, false)

    override fun setNightMode(isNight: Boolean) {
        prefs.edit()
                .putBoolean(PREF_NIGHT_MODE, isNight)
                .apply()
    }

    companion object {
        private const val PREF_LANGUAGE = "LANGUAGE"
        private const val PREF_LAST_NUMBER = "LAST_NUMBER"
        private const val PREF_NIGHT_MODE = "NIGHT_MODE"
    }
}