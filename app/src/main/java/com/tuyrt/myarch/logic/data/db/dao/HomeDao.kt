package com.tuyrt.myarch.logic.data.db.dao

import androidx.room.*
import com.tuyrt.myarch.logic.data.entity.BannerBean
import com.tuyrt.myarch.logic.data.entity.HomeListBean

/**
 * Created by tuyrt7 on 2021/12/16.
 * 说明：
 */
@Dao
interface HomeDao {

    @Query("SELECT * FROM HOME_DATA WHERE curPage = :page")
    suspend fun getHomeList(page: Int): HomeListBean?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(homeListBean: HomeListBean)

    @Query("DELETE FROM HOME_DATA")
    suspend fun deleteHomeAll()

    @Update
    suspend fun updataData(homeListBean: HomeListBean)

    @Delete
    suspend fun deleteData(vararg data: HomeListBean)

    //Banner
    @Query("SELECT * FROM BANNER")
    suspend fun getBannerList(): List<BannerBean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBanner(banners: List<BannerBean>)

    @Query("DELETE FROM BANNER")
    suspend fun deleteBannerAll()
}