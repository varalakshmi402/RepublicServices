package com.varalakshmiakella.republicServices.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.varalakshmiakella.republicServices.model.Driver
import com.varalakshmiakella.republicServices.model.Route


@Database(entities = [Driver::class,Route::class],
    version =1)
abstract class DriverDatabase :RoomDatabase(){

    abstract fun getDriverDao(): DriverDAO

    companion object{
        @Volatile
        private var instance: DriverDatabase?= null // Other threads can immediately see if any thread changes instance
        private val LOCK = Any() // To synchronize the instance

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            // this means instance value cannot be changed by some other thread when one thread is actually using it.
            instance ?: createDataBase(context).also{ instance =it}
        }
        private fun createDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DriverDatabase::class.java,
                "driver_details_db.db"
            ).fallbackToDestructiveMigration().build()
    }
}