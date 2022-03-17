/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [FirebaseRepositoryImpl.kt] created by Ji Sungbin on 22. 2. 28. 오후 9:01
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.firebase.repository

import android.graphics.Bitmap
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.suspendCancellableCoroutine
import team.applemango.runnerbe.domain.firebase.repository.FirebaseRepository
import java.io.ByteArrayOutputStream
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

private const val FirebaseStoragePath = "user-content"
private val ImageUpdateExceptionWithNull =
    Exception("Image upload is fail. But, exception is null.")

class FirebaseRepositoryImpl : FirebaseRepository {

    private val storageRef by lazy { Firebase.storage.reference }

    override suspend fun uploadImage(
        image: Bitmap,
        path: String,
        userId: Int,
    ): String = suspendCancellableCoroutine { continuation ->
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        storageRef.child(FirebaseStoragePath)
            .child(userId.toString())
            .child(path)
            .run {
                putBytes(data)
                    .continueWithTask { downloadUrl }
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful && task.result != null) {
                            continuation.resume(task.result.toString())
                        } else {
                            val exception = task.exception ?: ImageUpdateExceptionWithNull
                            continuation.resumeWithException(exception)
                        }
                    }
            }
    }

    override suspend fun loadConfigData(name: String): String {
        TODO("Not yet implemented")
    }
}
