/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [mapper.kt] created by Ji Sungbin on 22. 2. 7. 오후 7:52
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.register.login.mapper.email

import team.applemango.runnerbe.data.register.login.model.email.CheckDuplicateEmailResponse
import team.applemango.runnerbe.shared.domain.requireFieldExceptionMessage

internal fun CheckDuplicateEmailResponse.toBoolean() =
    checkNotNull(isSuccess) { requireFieldExceptionMessage("isSuccess") }
