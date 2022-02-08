/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [StringAsset.kt] created by Ji Sungbin on 22. 2. 8. 오후 6:56
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.asset

internal object StringAsset {
    const val Empty = ""

    object Title {
        const val ReadTerms = "먼저 이용약관을 읽고\n동의해주세요!"
        const val InputYear = "출생년도를 입력해주세요."
        const val SelectGender = "성별을 선택해주세요."
        const val WhatsJob = "어떤 직군에서 활동하시나요?"
        const val VerifyWithEmail = "회사 이메일로\n직장을 인증해주세요."
        const val VerifyWitheEmployeeId = "사진(ex. 사원증, 명함)으로\n직업을 인증해주세요!"
        const val EmailVerifyDone = "나를 충분히 소개했어요.\n달릴 준비 완료!"
        const val EmployeeIdVerifyRequestDone = "제출이 완료되었습니다.\n확인 후 알려드릴게요!"
    }

    object Subtitle {
        const val AgeVisibleDescription = "정확한 나이는 공개되지 않아요!\n20대 초반, 30대 중반 등으로 표기될 거예요."
        const val JobCanEditOnMypage = "추후 마이페이지에서 수정할 수 있어요!"
        const val VerifyWithEmail = "해당 정보는 러너님이 직장인임을 확인하는 용도로만\n사용되며, 외부에 공개되지 않습니다."
        const val VerifyWithEmployeeId = "해당 정보는 러너님이 직장인임을 확인하는 용도로만\n사용되며, 인증 후 안전하게 폐기됩니다."
        const val EmailVerifyDone = "이제 러너비에서 함께 달려볼까요?"
        const val EmployeeIdVerifyRequestDone =
            "소요 시간은 최대 6시간 정도이며, 완료 시 알림을\n보내드립니다. 그 전까지는 둘러보기만 가능헤요."
    }

    object Button {
        const val Next = "다음"
        const val NoEmail = "회사 이메일이 없어요"
        const val Verify = "인증하기"
        const val Start = "START!"
        const val GotoMain = "메인 화면으로"
    }

    object Content {
        const val CheckAllTerms = "모든 약관을 읽었으며, 이에 동의해요."
        const val CheckServiceTerm = "[필수] 서비스 이용약관 동의"
        const val CheckPersonalInformationTerm = "[필수] 개인정보 수집/이용 동의"
        const val CheckLocateTerm = "[필수] 위치기반 서비스 이용약관 동의"
    }

    object Gender {
        const val Female = "여성"
        const val Male = "남성"
    }

    object Hint {
        const val AgeNotice = "19세 미만은 이용할 수 없어요!"
        const val BaseEmail = "runnerbee@company.com"
        const val SentVerifyLink = "인증 링크가 발송되었어요\n메일이 오지 않는다면 스팸 메일함도 확인해주세요!"
        const val AlreadyUseEmail = "이미 사용 중인 이메일이에요!"
        const val RequireFieldJob = "👉   직장명, 직무/직위는 꼭 드러나야 해요!"
        const val RequireFieldInformation = "👉   정보를 식별할 수 있어야 해요."
        const val RequireFieldProtect = "👉   개인정보 보호를 위해 다른 정보는 가려주세요."
    }

    object Dialog {
        const val TitleVerifyNotice = "인증 확인까지 최대 6시간 정도가\n소요될 수 있어요!"
        const val Camera = "촬영하기"
        const val TakePhoto = "앨범에서 선택하기"
    }

    object Toast {
        const val NonInstallBrowser = "설치돼 있는 브라우저 앱이 없어요 \uD83D\uDE22"
    }
}
