package com.maina.ciaAgendi.data.model

import android.arch.persistence.room.Entity
import java.util.*

@Entity(tableName = "hymnals")
class LocalHymnal : Hymnal() {

    var lastAccessed: Date? = null

    companion object {

        val default: LocalHymnal = LocalHymnal().apply {
            code = "eng"
            name = "Christ In Song"
            language = "English"
            available = true
            lastAccessed = Date()
        }

        fun fromHymnal(hymnal: Hymnal): LocalHymnal {

            val local = LocalHymnal()

            local.name = hymnal.name
            local.language = hymnal.language
            local.code = hymnal.code
            local.lastAccessed = Calendar.getInstance().time
            local.available = true

            return local
        }
    }
}