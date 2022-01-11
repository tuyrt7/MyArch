package com.tuyrt.architecture.capacity.network

import com.tuyrt.architecture.capacity.log.KLog
import com.tuyrt.architecture.capacity.network.response.*
import com.tuyrt.architecture.capacity.network.error.RequestException
import com.tuyrt.architecture.capacity.network.error.handleException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：
 */
abstract class BaseRepository {

    /**
     *  处理 本地 + 远程的数据发送 、缓存远程数据的策略
     * @param remoto 网络数据
     * @param local 本地数据
     * @param save 当网络请求成功后，保存数据等操作
     * @param isUseCache 是否使用缓存
     */
    protected suspend fun <T> cacheNetCall(
        remote: suspend () -> BaseResponse<T>,
        local: suspend () -> T?,
        save: suspend (T) -> Unit,
        isUserCache: (T?) -> Boolean = { true }
    ): BaseResponse<T> {
        val localData = local.invoke()
        return if (isUserCache(localData) && localData != null) {
            KLog.d("从缓存中拿：$localData")
            SuccessResponse(localData)
        } else {
            remote.invoke().let { response ->
                if (response.isSuccess()) {
                    response.getResData()?.also { save(it) }
                    response
                } else {
                    throw RequestException(response)
                }
            }
        }
    }

    /**
     *  普通协程请求
     */
    protected suspend fun <T> fire(
        block: suspend () -> BaseResponse<T>
    ): BaseResponse<T> = withContext(Dispatchers.IO) {
        var response: BaseResponse<T> = EmptyResponse()
        kotlin.runCatching {
            block.invoke()
        }.onSuccess { data: BaseResponse<T> ->
            response = handleHttpOk(data)
        }.onFailure { throwable ->
            response = handleHttpError(throwable)
        }
        response
    }

    /**
     *  Flow 请求
     */
    protected suspend fun <T> water(
        block: suspend () -> BaseResponse<T>
    ): Flow<BaseResponse<T>> = flow {
        emit(
            handleHttpOk(block.invoke())
        )
    }.onStart {
        emit(StartResponse())
    }.onCompletion {
        emit(CompletionResponse())
    }.catch { throwable: Throwable ->
        emit(FailureResponse(handleException(throwable)))
    }.flowOn(Dispatchers.IO)

    private fun <T> handleHttpError(throwable: Throwable): BaseResponse<T> {
        return FailureResponse(handleException(throwable))
    }

    /**
     * 返回200，但是还要判断isSuccess
     */
    private fun <T> handleHttpOk(data: BaseResponse<T>): BaseResponse<T> {
        return if (data.isSuccess()) {
            getHttpSuccessResponse(data.getResData())
        } else {
            handleServerExceptions(data.getResCode() ?: -1, data.getResMsg() ?: "服务器出错")
            FailureResponse(handleException(RequestException(data)))
        }
    }

    /**
     * 成功和数据为空的处理
     */
    private fun <T> getHttpSuccessResponse(data: T?): ApiResponse<T> {
        return if (data == null || (data is List<*> && (data as List<*>).isEmpty())) {
            EmptyResponse()
        } else {
            SuccessResponse(data)
        }
    }

    /**
     *  处理服务器 API Error
     */
    open fun handleServerExceptions(errorCode: Int, errorMsg: String) {
        // 处理后台返回特殊错误码，比如token过期...
    }

}