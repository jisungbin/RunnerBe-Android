/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [json.kt] created by Ji Sungbin on 22. 2. 6. 오후 4:35
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.util.extension

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

internal val JacksonObjectMapper = ObjectMapper().registerKotlinModule()
