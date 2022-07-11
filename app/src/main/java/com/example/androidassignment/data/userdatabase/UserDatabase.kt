package com.example.androidassignment.data.userdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidassignment.data.User

//UserDataBase class will represent a DB in Room library.
//*******  It's a singleton class *******
// 1) contains the DB holder
// 2) serves the main access point for the underlying
//    connection to our app's persisted, relational data

//In @Database annotation we will specify our entities (we have only one), Db version, exportSchema
@Database(entities = [User:: class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    //companion object -> it's like Java static initializer

    //we want create one and only instance of this UserDataBase class
    //therefore we will implement Singleton Design Pattern.
    companion object{
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase? {
            if(INSTANCE == null){
                //it was synchronized(this) and i changed to synchronized(UserDatabase::class.java)
                synchronized(UserDatabase::class.java){
                    if(INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            UserDatabase::class.java,
                            "user_database"
                        ).build()
                    }
                }
            }

            return INSTANCE
        }
    }
}