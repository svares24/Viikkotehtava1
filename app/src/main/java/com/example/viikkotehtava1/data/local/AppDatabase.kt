package com.example.viikkotehtava1.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.viikkotehtava1.data.local.dao.TaskDao.TaskDao
import com.example.viikkotehtava1.data.local.entity.Task

// @Database kertoo Room:ille mitä tauluja (entities) tietokanta sisältää
// version = tietokannan versio (kasvatetaan kun rakenne muuttuu)
// exportSchema = false → ei tallenna skeemaa tiedostoon (tuotannossa true)
@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // Abstrakti funktio, jonka kautta pääsee DAO:n metodeihin
    // Room generoi toteutuksen automaattisesti
    abstract fun taskDao(): TaskDao

    // Singleton-pattern: vain yksi tietokantayhteys koko sovelluksessa
    // Tämä estää usean yhtäaikaisen yhteyden ongelmat
    companion object {
        // @Volatile = muuttujan arvo näkyy heti kaikille säikeille
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Jos instanssi on jo olemassa, palauta se
            // synchronized = vain yksi säie kerrallaan voi luoda tietokannan
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,  // Application context (ei Activity)
                    AppDatabase::class.java,     // Tietokantaluokka
                    "task_database"              // Tietokantatiedoston nimi
                )
                    // VAROITUS: fallbackToDestructiveMigration() tuhoaa kaiken datan
                    // kun tietokannan versio muuttuu! Tuotannossa käytä migraatioita.
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}