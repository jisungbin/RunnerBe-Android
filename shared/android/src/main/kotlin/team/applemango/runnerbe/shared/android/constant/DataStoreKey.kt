/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [DataStoreKey.kt] created by Ji Sungbin on 22. 2. 7. 오후 9:38
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.android.constant

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import team.applemango.runnerbe.shared.android.constant.DataStoreKey.Login.Jwt
import team.applemango.runnerbe.shared.android.constant.DataStoreKey.Login.RegisterDone
import team.applemango.runnerbe.shared.android.constant.DataStoreKey.Login.UserId
import team.applemango.runnerbe.shared.android.constant.DataStoreKey.Login.Uuid
import team.applemango.runnerbe.shared.android.constant.DataStoreKey.Onboard.Email
import team.applemango.runnerbe.shared.android.constant.DataStoreKey.Onboard.Gender
import team.applemango.runnerbe.shared.android.constant.DataStoreKey.Onboard.Job
import team.applemango.runnerbe.shared.android.constant.DataStoreKey.Onboard.TermsAllCheck
import team.applemango.runnerbe.shared.android.constant.DataStoreKey.Onboard.Unregister
import team.applemango.runnerbe.shared.android.constant.DataStoreKey.Onboard.Year
import team.applemango.runnerbe.shared.android.constant.DataStoreKey.Write.AgeFilter
import team.applemango.runnerbe.shared.android.constant.DataStoreKey.Write.Gender
import team.applemango.runnerbe.shared.android.constant.DataStoreKey.Write.ItemType
import team.applemango.runnerbe.shared.android.constant.DataStoreKey.Write.Locate
import team.applemango.runnerbe.shared.android.constant.DataStoreKey.Write.MaxPeopleCount
import team.applemango.runnerbe.shared.android.constant.DataStoreKey.Write.RunningDate
import team.applemango.runnerbe.shared.android.constant.DataStoreKey.Write.Message
import team.applemango.runnerbe.shared.android.constant.DataStoreKey.Write.RunningTime
import team.applemango.runnerbe.shared.android.constant.DataStoreKey.Write.Title

/**
 * DataStore Key 모음
 */
object DataStoreKey {
    /**
     * @property Jwt 유저 JWT 값 - string
     * @property Uuid 유저 UUID 값 - string
     * @property UserId 유저 아이디 값 - int
     * @property RegisterDone 유저 회원가입 완료 여부 - boolean
     * 이메일 인증일 경우 한 번에 바로 가입이 되지만,
     * 사원증 인증일 경우 가입 승인이 필요함
     */
    object Login {
        val Jwt = stringPreferencesKey("login-jwt")
        val Uuid = stringPreferencesKey("login-uuid")
        val UserId = intPreferencesKey("login-userid")
        val RegisterDone = booleanPreferencesKey("login-register_done")
    }

    /**
     * @property Unregister 온보딩 건너뛰기 여부 - boolean
     * @property TermsAllCheck 모든 약관 동의 여부 - boolean
     * @property Year 출생 년도 - int
     * @property Gender 성별 enum class 는 아래와 같이 정의돼 있음
     * ```
     * enum class Gender(val string: String, val code: String)
     * ```
     * Gender enum class 의 string 값 - string
     * @property Job 직업 enum class 의 [Enum.name] 값 - string
     * @property Email 입력한 이메일 - string
     */
    object Onboard {
        val Unregister = booleanPreferencesKey("onboard-unregister")
        val TermsAllCheck = booleanPreferencesKey("onboard-terms_all_check") // 동의 여부 boolean
        val Year = intPreferencesKey("onboard-birthday") // 입력한 년도
        val Gender = stringPreferencesKey("onboard-gender") // Gender enum class string value
        val Job = stringPreferencesKey("onboard-job") // Job enum class name
        val Email = stringPreferencesKey("onboard-email") // 입력한 이메일
    }

    /**
     * @property Title 게시글 제목 - string
     * @property Message 게시글 메시지 - string
     * @property Gender 참여 가능 성별 - string
     * @property MaxPeopleCount 참여 가능 최대 인원 수 - int
     * @property AgeFilter 참여 가능 연령대 - string
     * @property ItemType 게시글 타입 - string
     * @property RunningDate 모임 시간 - long
     * @property RunningTime 예상 달리기 소요 시간 - string
     * @property Locate 모임 위치 - string
     */
    object Write {
        val Title = stringPreferencesKey("write-title")
        val Message = stringPreferencesKey("write-message")
        val Gender = stringPreferencesKey("write-gender")
        val MaxPeopleCount = intPreferencesKey("write-max_people_count")
        val AgeFilter = stringPreferencesKey("write-age_filter")
        val ItemType = stringPreferencesKey("write-item_type")
        val RunningDate = longPreferencesKey("write-meeting_date")
        val RunningTime = stringPreferencesKey("write-running_time")
        val Locate = stringPreferencesKey("write-running_locate")
    }
}
