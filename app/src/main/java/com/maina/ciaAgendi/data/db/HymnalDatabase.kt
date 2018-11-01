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

package com.maina.ciaAgendi.data.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.maina.ciaAgendi.data.db.dao.HymnalDao
import com.maina.ciaAgendi.data.db.dao.HymnsDao
import com.maina.ciaAgendi.data.model.Hymn
import com.maina.ciaAgendi.data.model.LocalHymnal
import com.maina.ciaAgendi.utils.HymnsUtil
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

@Database(entities = [(LocalHymnal::class), (Hymn::class)], version = 1)
@TypeConverters(DataTypeConverters::class)
abstract class HymnalDatabase : RoomDatabase() {

    abstract fun hymnalDao(): HymnalDao

    abstract fun hymnsDao(): HymnsDao

    companion object {
        private const val DATABASE_NAME = "cis_hymnal_db"

        @Volatile
        private var INSTANCE: HymnalDatabase? = null

        fun getInstance(context: Context): HymnalDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context, HymnalDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback(), CoroutineScope {
                    override val coroutineContext: CoroutineContext
                        get() = Dispatchers.IO

                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        /**
                         * Load default hymns when database is created
                         */
                        launch {
                            val sdah = getInstance(context)
//                            val sdahOld = getInstance(context)
                            val cisdb = getInstance(context)
                            val kikdb = getInstance(context)
                            val kikdbOld = getInstance(context)
                            val nzkdb = getInstance(context)

                            sdah.hymnalDao().insert(LocalHymnal.default)
//                            sdahOld.hymnalDao().insert(LocalHymnal.sdahold)
                            cisdb.hymnalDao().insert(LocalHymnal.christinsong)
                            kikdb.hymnalDao().insert(LocalHymnal.kikuyu_new)
                            kikdbOld.hymnalDao().insert(LocalHymnal.kikuyu_old)
                            nzkdb.hymnalDao().insert(LocalHymnal.nyimbozakristo)

                            val hymnsNew = HymnsUtil.getSDAHymnsNew(context)
//                            val hymnsOld = HymnsUtil.getSDAHymnsOld(context)
                            val CIShymns = HymnsUtil.getCISHymns(context)
                            val kikhymnsNew = HymnsUtil.getKikuyuHymnsNew(context)
                            val kikhymnsOld = HymnsUtil.getKikuyuHymnsOld(context)
                            val nzkhymn = HymnsUtil.getNZKHymns(context)

                            hymnsNew.forEach { sdah.hymnsDao().insert(it) }
//                            hymnsOld.forEach { sdahOld.hymnsDao().insert(it) }
                            CIShymns.forEach { cisdb.hymnsDao().insert(it) }
                            kikhymnsNew.forEach { kikdb.hymnsDao().insert(it) }
                            kikhymnsOld.forEach { kikdbOld.hymnsDao().insert(it) }
                            nzkhymn.forEach { nzkdb.hymnsDao().insert(it) }
                        }
                    }
                })
                .build()
    }
}