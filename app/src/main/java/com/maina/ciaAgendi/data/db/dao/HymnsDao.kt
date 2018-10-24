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

package com.maina.ciaAgendi.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.maina.ciaAgendi.data.model.Hymn

@Dao
interface HymnsDao : BaseDao<Hymn> {

    @Query("SELECT * FROM hymns WHERE id = :id")
    fun getHymn(id: String): Hymn

    @Query("SELECT * FROM hymns WHERE language = :language")
    fun getHymns(language: String): List<Hymn>

    @Query("SELECT * FROM hymns WHERE body LIKE :query OR editedBody LIKE :query ORDER BY language")
    fun search(query: String): List<Hymn>

    @Query("SELECT * FROM hymns WHERE collection = :collection")
    fun getCollection(collection: String): List<Hymn>
}