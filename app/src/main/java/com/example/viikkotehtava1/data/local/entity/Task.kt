package com.example.viikkotehtava1.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity kertoo Room:ille, että tämä luokka = tietokannan taulu
// tableName määrittää taulun nimen SQLite-tietokannassa
@Entity(tableName = "tasks")
data class Task(
    // @PrimaryKey = taulun pääavain (uniikki tunniste jokaiselle riville)
    // autoGenerate = true → Room generoi id:n automaattisesti (1, 2, 3, ...)
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // @ColumnInfo määrittää sarakkeen nimen tietokannassa
    // Kotlin-nimi "title" → tietokannan sarake "title"
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    // Oletusarvo false → uusi tehtävä on aina keskeneräinen
    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean = false,

    // Tallentaa luontiajan millisekunteina (epoch time)
    // System.currentTimeMillis() asettaa oletusarvon automaattisesti
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)