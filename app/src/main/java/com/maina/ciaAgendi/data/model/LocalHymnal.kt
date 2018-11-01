package com.maina.ciaAgendi.data.model

import android.arch.persistence.room.Entity
import java.util.*

@Entity(tableName = "hymnals")
class LocalHymnal : Hymnal() {

    var lastAccessed: Date? = null

    companion object {

        val default: LocalHymnal = LocalHymnal().apply {
            code = "sdah"
            name = "SDA Hymnal"
            language = "English"
            available = true
            lastAccessed = Date()
        }
//        val sdahold: LocalHymnal = LocalHymnal().apply {
//            code = "sdah_old"
//            name = "SDA Hymnal Old"
//            language = "English"
//            available = true
//            lastAccessed = Date()
//        }
        val christinsong: LocalHymnal = LocalHymnal().apply {
            code = "cis"
            name = "Christ in Song"
            language = "English"
            available = true
            lastAccessed = Date()
        }
        val nyimbozakristo: LocalHymnal = LocalHymnal().apply {
            code = "nzk"
            name = "Nyimbo za Krisro"
            language = "Kiswahili"
            available = true
            lastAccessed = Date()
        }

        val kikuyu_new: LocalHymnal = LocalHymnal().apply {
            code = "kik_new"
            name = "Nyimbo Cia Agendi"
            language = "Kikuyu"
            available = true
            lastAccessed = Date()
        }
        val kikuyu_old: LocalHymnal = LocalHymnal().apply {
            code = "kik_old"
            name = "Nyimbo Cia Agendi Old"
            language = "Kikuyu"
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