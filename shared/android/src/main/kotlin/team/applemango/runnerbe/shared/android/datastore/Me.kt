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
import team.applemango.runnerbe.domain.register.runnerbe.model.UserToken
import team.applemango.runnerbe.domain.runningitem.model.common.Locate

object Me {
    var locateInitialized = false

    // 37.5284197, 126.9327389
    @Suppress("ObjectPropertyName")
    private var _locate = MutableStateFlow(
        Locate(
            address = "여의도 한강공원",
            latitude = 126.9327389,
            longitude = 37.5284197
        )
    )
    val locate = _locate.asStateFlow()

    // TODO
    var token = UserToken()

    fun updateLocate(locate: Locate) {
        locateInitialized = true
        _locate.value = locate
    }
}
