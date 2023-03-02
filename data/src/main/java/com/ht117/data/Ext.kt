package com.ht117.data

import com.ht117.data.model.State
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import java.net.UnknownHostException

suspend inline fun <reified T, D> FlowCollector<State<D>>.getRequest(
    client: HttpClient,
    url: String,
    crossinline converter: (T) -> D) {

    emit(State.Loading)
    val response = client.get(urlString = url)
    if (response.status.value in 200..299) {
        val body = response.body<T>()

        emit(State.Result(converter.invoke(body)))
    } else {
        emit(State.Failed(AppErr.NetworkErr(response.status.value, response.status.description)))
    }
}

fun<T> Flow<State<T>>.handleError() = catch {
    when (it) {
        is UnknownHostException -> emit(State.Failed(AppErr.LostNetworkErr))
        else -> emit(State.Failed(AppErr.UnknownErr(it.message.orEmpty())))
    }
}
