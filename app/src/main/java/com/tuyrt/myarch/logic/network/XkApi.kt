package com.tuyrt.myarch.logic.network

import com.tuyrt.myarch.base.BaseRes
import com.tuyrt.myarch.logic.model.Secret
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by tuyrt7 on 2021/12/9.
 * 说明：
 */
interface XkApi {

    companion object {
        const val BASE_URL = "https://demo.bayair.cn:8066/xiaoke_doc/api.php/index/"
    }
    @FormUrlEncoded
    @POST("get_key")
    suspend fun getKey(
        @Field("code") code: String,
        @Field("terminal_id") terminalId: String = "xxxxxxxxxxxxxxxx"
    ): BaseRes<Secret>
}