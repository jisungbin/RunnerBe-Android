/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BoardActivity.kt] created by Ji Sungbin on 22. 2. 9. 오전 1:06
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.birjuvachhani.locus.Locus
import dagger.hilt.android.AndroidEntryPoint
import team.applemango.runnerbe.R
import team.applemango.runnerbe.databinding.ActivityMainBinding
import team.applemango.runnerbe.feature.home.board.BottomSheetState
import team.applemango.runnerbe.feature.home.board.BottomSheetStateListenerHolder
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

    private lateinit var binding: ActivityMainBinding

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.bnvMenu.itemIconTintList = null
        binding.statusBarColor.bringToFront()

        BottomSheetStateListenerHolder.setBottomSheetStateListener { state ->
            when (state) {
                BottomSheetState.Hidden -> {
                    binding.statusBarColor.setBackgroundColor(Color.TRANSPARENT)
                    binding.bnvMenu.bringToFront()
                }
                BottomSheetState.Expanding -> {
                    binding.fcvMain.bringToFront()
                }
                BottomSheetState.Expanded -> {
                    binding.statusBarColor.setBackgroundColor(Color.parseColor("#80000000")) // Black 50% alpha
                }
            }
            binding.statusBarColor.bringToFront()
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bnvMenu.setupWithNavController(navController)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets ->
            val types = WindowInsetsCompat.Type.systemBars()
            val systemBarsInset = windowInsets.getInsets(types)
            binding.statusBarColor.updateLayoutParams {
                height = systemBarsInset.top
            }
            windowInsets
        }
    }
}
