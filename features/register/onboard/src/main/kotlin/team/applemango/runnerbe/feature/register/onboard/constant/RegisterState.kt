/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RegisterState.kt] created by Ji Sungbin on 22. 2. 12. 오후 2:11
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.constant

import team.applemango.runnerbe.feature.register.onboard.OnboardViewModel
import team.applemango.runnerbe.feature.register.onboard.constant.RegisterState.DatabaseError
import team.applemango.runnerbe.feature.register.onboard.constant.RegisterState.DuplicateEmail
import team.applemango.runnerbe.feature.register.onboard.constant.RegisterState.DuplicateNickname
import team.applemango.runnerbe.feature.register.onboard.constant.RegisterState.DuplicateUuid
import team.applemango.runnerbe.feature.register.onboard.constant.RegisterState.ImageUploadError
import team.applemango.runnerbe.feature.register.onboard.constant.RegisterState.ImageUploading
import team.applemango.runnerbe.feature.register.onboard.constant.RegisterState.None
import team.applemango.runnerbe.feature.register.onboard.constant.RegisterState.NullInformation
import team.applemango.runnerbe.feature.register.onboard.constant.RegisterState.RegisterDone
import team.applemango.runnerbe.feature.register.onboard.constant.RegisterState.VerifyRequestDone

/**
 * [OnboardViewModel.requestUserRegister] state enum class
 *
 * @property None 아무것도 하지 않은 초기 상태
 * @property ImageUploading 이미지(사원증 사진) 업로드중
 * @property ImageUploadError 이미지(사원증 사진) 업로드 실패
 * @property RegisterDone 가입 성공 (1000)
 * @property VerifyRequestDone 사원증으로 인증을 진행했을 경우, 인증 요청 완료한 상태 -> 가입 성공 X, 가입 대기 상태 O
 * @property DuplicateUuid 중복된 uuid (3001)
 * @property DuplicateEmail 중복된 이메일 (3002)
 * @property DuplicateNickname 중복된 닉네임 (랜덤 6자리 숫자로 자동 생성 됐지만 중복됨) (3004)
 * @property DatabaseError 서버 데이터베이스 에러 (4000)
 * @property NullInformation 회원가입에 필수적인 정보가 null 임
 */
internal enum class RegisterState {
    None,
    ImageUploading,
    ImageUploadError,
    RegisterDone,
    VerifyRequestDone,
    DuplicateUuid,
    DuplicateEmail,
    DuplicateNickname,
    DatabaseError,
    NullInformation
}
