package com.appringer.common.communicator


/**
 * Created by kushaal singla on 26-Aug-18.
 */
class DataSource<T> private constructor(var code: Int, val data: T?, var msg: String) {
    val isSuccess: Boolean
        get() = code == APIResponseCodeEnum.SUCCESS.key


    companion object {
        fun <T> success(data: T?): DataSource<T> {
            return DataSource(APIResponseCodeEnum.SUCCESS.key, data, APIResponseCodeEnum.SUCCESS.msg)
        }

        fun <T> error(msg: String): DataSource<T> {
            return DataSource(APIResponseCodeEnum.FAILED.key, null, msg)
        }

        fun <T> error(codeEnum: APIResponseCodeEnum, msg: String): DataSource<T> {
            return DataSource(codeEnum.key, null, msg)
        }

        fun <T> error(codeEnum: APIResponseCodeEnum): DataSource<T?> {
            return DataSource(codeEnum.key, null, codeEnum.msg)
        }
    }

}