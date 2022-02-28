/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [AccessToken.kt] created by Ji Sungbin on 22. 2. 7. 오후 8:31
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.register.runnerbe.model

/**
 * API Call 에 Body 로 필요함
 *
 * value class 로 하면 당연히 inline 되면서 그냥 String 으로 들어감
 */
data class AccessToken(val accessToken: String)
