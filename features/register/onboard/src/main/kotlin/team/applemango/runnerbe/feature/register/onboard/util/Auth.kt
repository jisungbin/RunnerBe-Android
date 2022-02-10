/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Auth.kt] created by Ji Sungbin on 22. 2. 11. 오전 3:34
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.util

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private val alphabetRange = ('a'..'z') + ('A'..'Z') + (0..10)
private val randomPassword get() = List(10) { alphabetRange.random() }.joinToString("")
private val UserNullException =
    Exception("Firebase.auth.createUserWithEmailAndPassword success. But, current user is null.")

internal fun createUserWithEmailVerify(
    email: String,
    exceptionHandler: (Exception) -> Unit,
    emailSendSuccess: () -> Unit,
) {
    Firebase.auth
        .createUserWithEmailAndPassword(email, randomPassword)
        .addOnSuccessListener {
            Firebase.auth.currentUser?.let { user ->
                user.sendEmailVerification()
                    .addOnSuccessListener {
                        emailSendSuccess()
                    }.addOnFailureListener { exception ->
                        exceptionHandler(exception)
                    }
            } ?: run {
                exceptionHandler(UserNullException)
            }
        }
        .addOnFailureListener { exception ->
            exceptionHandler(exception)
        }
}
