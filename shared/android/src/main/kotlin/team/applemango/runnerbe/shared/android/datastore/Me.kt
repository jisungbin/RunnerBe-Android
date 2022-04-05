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

object Me {
    @Suppress("ObjectPropertyName")
    private var _locate = MutableStateFlow(
        Locate(
            address = "여의도 한강공원",
            latitude = 37.5284197,
            longitude = 126.9327389
        )
    )
    val locate = _locate.asStateFlow()
    var locateInitialized = false

    var token = UserToken()

    var genderFilter = Gender.All
    var ageFilter: AgeFilter = AgeFilter.None
    var jobFilter: JobFilter = JobFilter.None
    var distanceFilter: DistanceFilter = DistanceFilter.None

    fun updateLocate(locate: Locate) {
        locateInitialized = true
        _locate.value = locate
    }
}
