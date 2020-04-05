package com.appringer.common.communicator

enum class APIResponseCodeEnum(val key: Int, val msg: String) {
    SUCCESS(0, "Success"),
    FAILED(-1, "Failed")
}
