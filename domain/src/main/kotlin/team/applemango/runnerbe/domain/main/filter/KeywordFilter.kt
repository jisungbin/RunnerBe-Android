/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [KeywordFilter.kt] created by Ji Sungbin on 22. 2. 24. 오전 5:18
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.filter

sealed class KeywordFilter(val code: String) {
    object None : KeywordFilter("N")
    data class Create(val keyword: String) : KeywordFilter(keyword)
}
