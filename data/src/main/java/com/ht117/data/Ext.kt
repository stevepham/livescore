package com.ht117.data

import com.ht117.data.model.UiState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import java.net.UnknownHostException

suspend inline fun <reified T, D> FlowCollector<UiState<D>>.getRequest(
    client: HttpClient,
    url: String,
    crossinline converter: (T) -> D) {

    val response = client.get(urlString = url)
    if (response.status == HttpStatusCode.OK) {
        val body = response.body<T>()
        emit(UiState.Result(converter.invoke(body)))
    } else {
        emit(UiState.Failed(AppErr.NetworkErr(response.status.value, response.status.description)))
    }
}

fun<T> Flow<UiState<T>>.handleError() = catch {
    when (it) {
        is UnknownHostException -> emit(UiState.Failed(AppErr.LostNetworkErr))
        else -> emit(UiState.Failed(AppErr.UnknownErr(it.message.orEmpty())))
    }
}
