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

package com.maina.ciaAgendi.utils

import android.content.Context
import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.maina.ciaAgendi.R
import com.maina.ciaAgendi.data.model.Hymn
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter

object HymnsUtil {

    private val gson = Gson()

    fun getHymns(context: Context): List<Hymn> {
        val jsonString = getJson(context.resources, R.raw.sdah_new)
        val type = object : TypeToken<List<Hymn>>() {

        }.type

        val hymns: List<Hymn> = gson.fromJson(jsonString, type)
        hymns.forEach {
            val number = hymns.indexOf(it) + 1
            it.id = "eng_$number"
            it.number = number
            it.language = "eng"
        }
        return hymns
    }

    /**
     * Open a json file from raw and construct as class using Gson.
     *
     * @param resources
     * @param resId
     * @return
     */
    fun getJson(resources: Resources, resId: Int): String {

        val resourceReader = resources.openRawResource(resId)
        val writer = StringWriter()

        try {
            val reader = BufferedReader(InputStreamReader(resourceReader, "UTF-8"))
            var line: String? = reader.readLine()
            while (line != null) {
                writer.write(line)
                line = reader.readLine()
            }
            return writer.toString()
        } catch (e: Exception) {
            Timber.e("Unhandled exception while using JSONResourceReader")
        } finally {
            try {
                resourceReader.close()
            } catch (e: Exception) {
                Timber.e(e, "Unhandled exception while using JSONResourceReader")
            }

        }
        return ""
    }
}