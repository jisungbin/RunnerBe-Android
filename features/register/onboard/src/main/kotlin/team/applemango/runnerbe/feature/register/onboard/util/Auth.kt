/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Auth.kt] created by Ji Sungbin on 22. 2. 11. 오전 3:34
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.util

import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.jisungbin.logeukes.LoggerType
import io.github.jisungbin.logeukes.logeukes

private val alphabetRange = ('a'..'z') + ('A'..'Z') + (0..10)
private val randomPassword get() = List(10) { alphabetRange.random() }.joinToString("")

private val SendEmailExceptionWithNoMessage =
    Exception("user.sendEmailVerification failure. But, exception message is null.")
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
                user.sendEmailVerification(
                    actionCodeSettings {
                        url = "https://runnerbe-auth.shop/verify=true"
                        handleCodeInApp = true // 필수!
                        setAndroidPackageName(
                            "team.applemango.runnerbe", // 리다이렉트될 앱 패키지명
                            true, // 이용 불가능시 플레이스토어 이동해서 설치 요청
                            "21" // min sdk level
                        )
                    }
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emailSendSuccess()
                    } else {
                        exceptionHandler(task.exception ?: SendEmailExceptionWithNoMessage)
                    }
                    // 이 메일로 끝까지 회원가입 안 했을떈 메일 다시 쓸 수 있게 하기 위해 유저 삭제
                    // 러너비 서버에서 따로 이메일 중복 검사 절차 거침
                    user.delete()
                        .addOnSuccessListener {
                            logeukes { "$email 유저 삭제 완료" }
                        }.addOnFailureListener { exception ->
                            logeukes(type = LoggerType.E) { "$email 유저 삭제 실패: $exception" }
                        }
                }
            } ?: run {
                exceptionHandler(UserNullException)
            }
        }
        .addOnFailureListener { exception ->
            exceptionHandler(exception)
        }
}
