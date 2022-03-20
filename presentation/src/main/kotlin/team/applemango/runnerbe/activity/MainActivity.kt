/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainActivity.kt] created by Ji Sungbin on 22. 3. 20. 오후 11:08
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import team.applemango.runnerbe.R
import team.applemango.runnerbe.databinding.ActivityMainBinding
import team.applemango.runnerbe.shared.android.constant.BottomNavigationBarHeight
import team.applemango.runnerbe.shared.android.extension.setWindowInsets
import team.applemango.runnerbe.shared.domain.unit.dp
import team.applemango.runnerbe.shared.domain.unit.toInt

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

        setWindowInsets()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.bnvMenu.itemIconTintList = null

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bnvMenu.updateLayoutParams {
            height = BottomNavigationBarHeight.dp.toInt()
        }
        binding.bnvMenu.setupWithNavController(navController)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
            val types = WindowInsetsCompat.Type.systemBars()
            val systemBarsInset = windowInsets.getInsets(types)
            binding.viewNavigationBarColor.updateLayoutParams {
                height = systemBarsInset.bottom
            }
            windowInsets
        }
    }
}
