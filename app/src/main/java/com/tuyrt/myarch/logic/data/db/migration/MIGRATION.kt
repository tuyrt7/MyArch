package com.tuyrt.myarch.logic.data.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Created by tuyrt7 on 2021/12/16.
 * 说明：
 */
object MIGRATION {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // 没有更改表结构，空实现
            database.execSQL("create table banner(id INTEGER NOT NULL primary key,`desc` TEXT NOT NULL,imagePath TEXT NOT NULL,isVisible INTEGER NOT NULL,`order` INTEGER NOT NULL, title TEXT NOT NULL,type INTEGER NOT NULL,url TEXT NOT NULL)")
        }
    }
}