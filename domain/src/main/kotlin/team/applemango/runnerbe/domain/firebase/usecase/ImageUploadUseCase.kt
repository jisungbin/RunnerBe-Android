/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UploadImageUseCase.kt] created by Ji Sungbin on 22. 2. 28. 오후 9:28
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.firebase.usecase

import android.graphics.Bitmap
import team.applemango.runnerbe.domain.firebase.repository.FirebaseRepository

class ImageUploadUseCase(private val repo: FirebaseRepository) {
    suspend operator fun invoke(
        image: Bitmap,
        path: String,
        userId: Int,
    ) = runCatching {
        repo.uploadImage(
            image = image,
            path = path,
            userId = userId,
        )
    }
}
