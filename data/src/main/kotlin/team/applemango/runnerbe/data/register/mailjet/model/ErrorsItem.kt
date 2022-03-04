/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [ErrorsItem.kt] created by Ji Sungbin on 22. 2. 13. 오후 9:55
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.register.mailjet.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorsItem(
    @field:JsonProperty("ErrorIdentifier")
    val errorIdentifier: String? = null,

    @field:JsonProperty("ErrorCode")
    val errorCode: String? = null,

    @field:JsonProperty("ErrorRelatedTo")
    val errorRelatedTo: List<String?>? = null,

    @field:JsonProperty("ErrorMessage")
    val errorMessage: String? = null,

    @field:JsonProperty("StatusCode")
    val statusCode: Int? = null,
)
