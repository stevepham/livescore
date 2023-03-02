package com.ht117.data

sealed class AppErr {
    object LostNetworkErr: AppErr()
    data class NetworkErr(val code: Int, val message: String): AppErr()
    data class UnknownErr(val message: String): AppErr()
}
