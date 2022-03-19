/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Locate.kt] created by Ji Sungbin on 22. 2. 24. 오전 5:17
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.model.common

import team.applemango.runnerbe.shared.domain.constant.EmptyString

private const val DefaultAddress = EmptyString
private const val DefaultLatitude = Double.NaN
private const val DefaultLongitude = Double.NaN

val DefaultLocate = Locate(
    address = DefaultAddress,
    latitude = DefaultLatitude,
    longitude = DefaultLongitude
)

/**
 * @property address 주소 (ex_석림1로 20)
 * @property latitude 위도
 * @property longitude 경도
 */
data class Locate(
    val address: String,
    val latitude: Double,
    val longitude: Double,
)
