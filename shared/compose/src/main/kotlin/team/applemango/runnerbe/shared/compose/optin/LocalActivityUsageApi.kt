/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [ExperimentalPermissionsApi.kt] created by Ji Sungbin on 22. 3. 21. 오후 2:16
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.optin

@RequiresOptIn(message = "This API uses LocalActivity. Check if the Activity is being provided.")
@Retention(AnnotationRetention.BINARY)
annotation class LocalActivityUsageApi
