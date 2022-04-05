/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [ProjectConstants.kt] created by Ji Sungbin on 22. 1. 31. 오후 9:59
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

// enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS") 버그이씀 (새로운 모듈 인식 안됨)
@Suppress("SpellCheckingInspection")
object ProjectConstants {
    const val Di = ":di"
    const val Data = ":data"
    const val Domain = ":domain"
    const val SharedAndroid = ":shared:android"
    const val SharedDomain = ":shared:domain"
    const val SharedCompose = ":shared:compose"
    const val Mail = ":features:mail"
    const val MyPage = ":features:mypage"
    const val Presentation = ":presentation"
    const val HomeWrite = ":features:home:write"
    const val HomeBoard = ":features:home:board"
    const val HomeDetail = ":features:home:detail"
    const val HomeFilter = ":features:home:filter"
    const val HomeNotification = ":features:home:notification"
    const val RegisterSnsLogin = ":features:register:snslogin"
    const val RegisterOnboard = ":features:register:onboard"
    const val XmlSuperWheelPicker = ":xml:superwheelpicker"
    const val XmlPhotoEditor = ":xml:photoeditor"
    const val XmlRangePicker = ":xml:rangepicker"
}
