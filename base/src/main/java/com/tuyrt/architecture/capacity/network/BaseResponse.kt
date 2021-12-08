package com.tuyrt.architecture.capacity.network

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：
 */
abstract class BaseResponse<T> {

    abstract val success:Boolean

    abstract val data: T?

    abstract val errorCode: Int?

    abstract val errorMsg:String?

}

