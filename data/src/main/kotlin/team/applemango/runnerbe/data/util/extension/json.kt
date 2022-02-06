/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [json.kt] created by Ji Sungbin on 22. 2. 6. 오후 4:35
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.util.extension

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

@PublishedApi
internal val mapper by lazy {
    ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
        .registerKotlinModule()
}

internal inline fun <reified T> String.toModel(): T = mapper.readValue(this, T::class.java)
    ?: throw Exception("문자열을 json 모델로 바꾸는데 오류가 발생했어요.\n\n($this)")

internal fun Any.toJsonString() = mapper.writeValueAsString(this)
    ?: throw Exception("json 모델을 문자열로 바꾸는데 오류가 발생했어요.\n\n($this)")
