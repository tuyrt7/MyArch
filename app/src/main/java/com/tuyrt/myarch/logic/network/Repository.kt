package com.tuyrt.myarch.logic.network

import com.tuyrt.architecture.capacity.network.BaseRepository
import com.tuyrt.myarch.logic.network.biz.MyNetwork
import com.tuyrt.myarch.logic.network.biz.XkNetwork

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：
 */
object Repository : BaseRepository() {

    suspend fun login(pwd: String) = fire {
        MyNetwork.login(pwd)
    }

    suspend fun loginFlow(pwd: String) = water {
        MyNetwork.login(pwd)
    }

     suspend fun getKey(code: String) = water { XkNetwork.getKey(code) }
}