package com.andreromano.foodmix.network

import com.andreromano.foodmix.core.ErrorKt
import com.andreromano.foodmix.core.ResultKt
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

class FromBaseResponseToResultKtAdapter<T>(private val adapter: JsonAdapter<T>) : JsonAdapter<ResultKt<T>>() {
    override fun fromJson(reader: JsonReader): ResultKt<T> {
        val response =  adapter.fromJson(reader)

        return when {
            response == null -> ResultKt.Failure(ErrorKt.Generic)
//            response.status.status == "NOK" -> ResultKt.Failure(Resource.Error.from(ApiErrorConverter.parseError(response.status)))
            // response.data == null -> return error here TODO: Maybe we need to do this here? not sure
            else -> ResultKt.Success(response)
        }
    }

    override fun toJson(writer: JsonWriter, value: ResultKt<T>?) {
        throw UnsupportedOperationException()
    }
}