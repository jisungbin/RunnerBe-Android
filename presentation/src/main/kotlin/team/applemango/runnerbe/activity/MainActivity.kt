/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BoardActivity.kt] created by Ji Sungbin on 22. 2. 9. 오전 1:06
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ModalBottomSheetLayout
import com.birjuvachhani.locus.Locus
import dagger.hilt.android.AndroidEntryPoint
import team.applemango.runnerbe.shared.android.datastore.Me
import team.applemango.runnerbe.shared.android.extension.setWindowInsetsUsage

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // TODO: Firebase Analytics 설정
    // https://firebase.google.com/docs/analytics/userid
    // https://firebase.google.com/docs/analytics/user-properties
    // https://firebase.google.com/docs/analytics/configure-data-collection

    // TODO: Firebase Crashlytics 설정
    // https://firebase.google.com/docs/crashlytics/customize-crash-reports

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // getCurrentLocate 로 하면 작동하지 않음
        Locus.startLocationUpdates(this) { result ->
            result.location?.let { location ->
                Me.updateLocate(
                    Me.locate.value.copy(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                )
                Locus.stopLocationUpdates()
            }
        }

        setWindowInsetsUsage()
        setContent {
            ModalBottomSheetLayout(
                sheetContent = {

                }
            )
        }
    }
}