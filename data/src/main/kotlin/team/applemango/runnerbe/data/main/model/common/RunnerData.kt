/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunnerInfoItem.kt] created by Ji Sungbin on 22. 2. 24. 오후 10:24
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.main.model.common

import com.fasterxml.jackson.annotation.JsonProperty

data class RunnerData(
    @field:JsonProperty("gender")
    val gender: String? = null,

    @field:JsonProperty("nickName")
    val nickName: String? = null,

    @field:JsonProperty("job")
    val job: String? = null,

    @field:JsonProperty("userId")
    val userId: Int? = null,

    @field:JsonProperty("profileImageUrl")
    val profileImageUrl: String? = null,

    @field:JsonProperty("age")
    val age: String? = null,

    @field:JsonProperty("diligence")
    val diligence: String? = null,
)
