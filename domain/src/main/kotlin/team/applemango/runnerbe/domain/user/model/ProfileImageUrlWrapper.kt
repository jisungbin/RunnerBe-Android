/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [ProfileImageUrl.kt] created by Ji Sungbin on 22. 3. 1. 오전 10:21
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.user.model

/**
 * API Call 에 Body 로 필요함
 *
 * value class 로 하면 당연히 inline 되면서 그냥 String 으로 들어감
 */
data class ProfileImageUrlWrapper(val profileImageUrl: String)