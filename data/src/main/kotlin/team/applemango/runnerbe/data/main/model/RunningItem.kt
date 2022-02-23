/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [ResultItem.kt] created by Ji Sungbin on 22. 2. 24. 오전 2:57
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.main.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RunningItem(
    @field:JsonProperty("locationInfo")
    val locationInfo: String? = null,

    @field:JsonProperty("DISTANCE")
    val distance: String? = null,

    @field:JsonProperty("gender")
    val gender: String? = null,

    @field:JsonProperty("gatherLatitude")
    val gatherLatitude: String? = null,

    @field:JsonProperty("nickName")
    val nickName: String? = null,

    @field:JsonProperty("bookMarkNumber")
    val bookMarkNumber: Int? = null,

    @field:JsonProperty("postId")
    val postId: Int? = null,

    @field:JsonProperty("postingTime")
    val postingTime: String? = null,

    @field:JsonProperty("title")
    val title: String? = null,

    @field:JsonProperty("gatheringTime")
    val gatheringTime: String? = null,

    @field:JsonProperty("runningTag")
    val runningTag: String? = null,

    @field:JsonProperty("whetherEnd")
    val whetherEnd: String? = null,

    @field:JsonProperty("postUserId")
    val postUserId: Int? = null,

    @field:JsonProperty("gatherLongitude")
    val gatherLongitude: String? = null,

    @field:JsonProperty("job")
    val job: String? = null,

    @field:JsonProperty("profileImageUrl")
    val profileImageUrl: Any? = null,

    @field:JsonProperty("age")
    val age: String? = null,
)
