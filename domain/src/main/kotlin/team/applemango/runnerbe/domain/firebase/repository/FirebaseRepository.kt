/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [FirebaseRepository.kt] created by Ji Sungbin on 22. 2. 28. 오후 6:45
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.firebase.repository

import android.graphics.Bitmap

interface FirebaseRepository {
    /**
     * Firebase Storage 에 이미지 업로드
     *
     * TODO: platform independent
     * 인자에 들어가는 Bitmap 은 안드로이드에 platform aware 함
     * 이를 수정해야 함!! (related issue: [#38](https://github.com/applemango-runnerbe/RunnerBe-Android/issues/38))
     *
     * @return Firebase Storage 에 올라간 이미지 다운로드 주소
     */
    suspend fun uploadImage(image: Bitmap, name: String): String

    /**
     * Firebase Remote Config 조회 (현재는 업데이트 체크용으로 존재)
     *
     * TODO: return type
     * 현재는 return 값이 [String] 으로 고정돼 있지만,
     * 추후 다른 타입도 지원할 것인지 개발하면서 결정이 필요함
     */
    suspend fun loadConfig(name: String): String
}