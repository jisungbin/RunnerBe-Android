/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [TestRepoImpl.kt] created by Ji Sungbin on 22. 2. 1. 오전 11:01
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.di.test

import io.github.jisungbin.logeukes.logeukes
import javax.inject.Inject

class TestRepoImpl @Inject constructor() : TestRepo {
    override fun print() {
        logeukes { "HI!" }
    }
}
