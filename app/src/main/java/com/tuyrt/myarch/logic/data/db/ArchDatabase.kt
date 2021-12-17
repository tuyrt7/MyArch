package com.tuyrt.myarch.logic.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.blankj.utilcode.util.Utils
import com.tuyrt.myarch.logic.data.db.dao.HomeDao
import com.tuyrt.myarch.logic.data.db.migration.MIGRATION
import com.tuyrt.myarch.logic.data.entity.BannerBean
import com.tuyrt.myarch.logic.data.entity.HomeListBean

/**
 * Created by tuyrt7 on 2021/12/16.
 * 说明：
 */
@Database(entities = [HomeListBean::class, BannerBean::class], version = 2, exportSchema = false)
abstract class ArchDatabase : RoomDatabase() {

    abstract fun homeLocalData(): HomeDao

    companion object {

        fun get(): ArchDatabase {
            return SingletonHolder.INSTANCE
        }
    }

    private object SingletonHolder {
        private const val DB_NAME = "arch_db"

        val INSTANCE = Room.databaseBuilder(Utils.getApp(), ArchDatabase::class.java, DB_NAME)
            .addMigrations(MIGRATION.MIGRATION_1_2)
            .build()
    }
}