/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [SharedData.kt] created by Ji Sungbin on 22. 3. 24. 오후 8:35
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.android.datastore

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.domain.register.runnerbe.model.UserToken
import team.applemango.runnerbe.domain.runningitem.filter.AgeFilter
import team.applemango.runnerbe.domain.runningitem.filter.DistanceFilter
import team.applemango.runnerbe.domain.runningitem.filter.JobFilter
import team.applemango.runnerbe.domain.runningitem.model.common.Locate

@Suppress("ObjectPropertyName")
object Me {
    private val _locate = MutableStateFlow(
        Locate(
            address = "여의도 한강공원",
            latitude = 37.5284197,
            longitude = 126.9327389
        )
    )
    val locate = _locate.asStateFlow()
    var locateInitialized = false

    var token = UserToken()

    private val _genderFilter = MutableStateFlow(Gender.All)
    val genderFilter = _genderFilter.asStateFlow()

    private val _ageFilter = MutableStateFlow(AgeFilter.None)
    val ageFilter = _ageFilter.asStateFlow()

    private val _jobFilter = MutableStateFlow<JobFilter>(JobFilter.None)
    val jobFilter = _jobFilter.asStateFlow()

    private val _distanceFilter = MutableStateFlow<DistanceFilter>(DistanceFilter.None)
    val distanceFilter = _distanceFilter.asStateFlow()

    fun updateLocate(locate: Locate) {
        locateInitialized = true
        _locate.value = locate
    }

    fun updateGenderFilter(gender: Gender) {
        _genderFilter.value = gender
    }

    fun updateAgeFilter(ageFilter: AgeFilter) {
        _ageFilter.value = ageFilter
    }

    fun updateJobFilter(jobFilter: JobFilter) {
        _jobFilter.value = jobFilter
    }

    fun updateDistanceFilter(distanceFilter: DistanceFilter) {
        _distanceFilter.value = distanceFilter
    }
}
