package com.tuyrt.myarch.logic.data

import com.tuyrt.architecture.capacity.log.KLog
import com.tuyrt.architecture.capacity.network.BaseRepository
import com.tuyrt.architecture.capacity.network.data.BaseResponse
import com.tuyrt.architecture.capacity.network.data.SuccessResponse
import com.tuyrt.architecture.capacity.network.error.RequestException
import com.tuyrt.myarch.logic.data.db.ArchDatabase
import com.tuyrt.myarch.logic.data.entity.LoginModel
import com.tuyrt.myarch.logic.data.http.MyNetwork
import com.tuyrt.myarch.logic.data.http.XkNetwork

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：
 */
object Repository : BaseRepository() {

    private val homeLocal = ArchDatabase.get().homeLocalData()

    suspend fun login(pwd: String): BaseResponse<LoginModel> = fire { MyNetwork.login(pwd) }

    suspend fun loginFlow(pwd: String) = water {
        MyNetwork.login(pwd)
    }

    suspend fun getKeyFlow(code: String) = water {
        XkNetwork.getKey(code)
    }

    suspend fun getBannerData(refresh: Boolean = false) = water {
        cacheNetCall(
            remote = { MyNetwork.getBannerData() },
            local = { homeLocal.getBannerList() },
            save = {
                if (refresh) {
                    homeLocal.deleteBannerAll()
                }
                KLog.d("开始缓存Banner：$it")
                homeLocal.insertBanner(it)
            },
            isUserCache = {
                !refresh && !it.isNullOrEmpty()
            }
        )
    }

    suspend fun getHomeList(page: Int, refresh: Boolean) = water {
        cacheNetCall(
            remote = { MyNetwork.getHomeListData(page) },
            local = { homeLocal.getHomeList(page + 1) },
            save = {
                if (refresh) homeLocal.deleteHomeAll()
                KLog.d("开始缓存HomeListData：$it")
                homeLocal.insertData(it)
            },
            isUserCache = {
                !refresh
            }
        )
    }


}