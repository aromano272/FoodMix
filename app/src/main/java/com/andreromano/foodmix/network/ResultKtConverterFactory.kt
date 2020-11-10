package com.andreromano.foodmix.network

import com.andreromano.foodmix.core.ResultKt
import com.andreromano.foodmix.core.ResultKt.Success
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object ResultKtConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        if (getRawType(type) != ResultKt::class.java) return null

        val successType = (type as ParameterizedType).actualTypeArguments[0]

        val delegateConverter = retrofit.nextResponseBodyConverter<Any>(
            this,
            successType,
            annotations
        )
        return ApiResultConverter(delegateConverter)
    }

    private class ApiResultConverter(
        private val delegate: Converter<ResponseBody, Any>
    ) : Converter<ResponseBody, ResultKt<*>> {
        override fun convert(value: ResponseBody): ResultKt<*>? {
            return delegate.convert(value)?.let(::Success)
        }
    }
}