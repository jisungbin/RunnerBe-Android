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
    const val OK = "확인했어요"

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
            "소요 시간은 <u>최대 6시간</u> 정도이며, 완료 시 알림을\n보내드립니다. 그 전까지는 둘러보기만 가능헤요."
    }

    object Button {
        const val Next = "다음"
        const val NoEmail = "회사 이메일이 없어요"
        const val Verify = "인증하기"
        const val ReVerify = "재인증"
        const val Start = "START!"
        const val GotoMain = "메인 화면으로"
    }

    object Content {
        const val CheckAllTerms = "모든 약관을 읽었으며, 이에 동의해요."
        const val CheckServiceTerm = "[필수] 서비스 이용약관 동의"
        const val CheckPersonalInformationTerm = "[필수] 개인정보 수집/이용 동의"
        const val CheckLocateTerm = "[필수] 위치기반 서비스 이용약관 동의"
    }

    object Hint {
        const val AgeNotice = "19세 미만은 이용할 수 없어요!"
        const val PlaceholderEmail = "runnerbee@company.com"
        const val EmailSendingRequest = "인증 링크 발송을 요청중이에요."
        val SentVerifyLink = """
            인증 링크를 발송했어요!
            최대 5분 내에 도착할 거예요 🐝🐝 💨
            메일이 오지 않는다면 스팸 메일함도 확인해주세요.
        """.trimIndent() // 🐝🐝 💨 이모지 때문에 너무 길어져서 """ 처리
        const val DuplicateEmail = "이미 사용 중인 이메일이에요!"
        const val ErrorUuid = "회원가입중에 오류가 발생했어요.\n초기 단계부터 다시 진행해 주세요."
        const val RequireFieldJob = "👉   <b><u>직장명, 직무/직위</u></b>는 꼭 드러나야 해요!"
        const val RequireFieldInformation = "👉   정보를 식별할 수 있어야 해요."
        const val RequireFieldProtect = "👉   개인정보 보호를 위해 다른 정보는 가려주세요."
    }

    object Dialog {
        const val EmailVerifyLinkNotice = "메일 인증은 현재 접속하신\n기기 내에서만 가능합니다!"
        const val EmailVerifyTimeNotice = "인증 확인까지 최대 6시간 정도가\n소요될 수 있어요!"
        const val FromCamera = "촬영하기"
        const val FromAlbum = "앨범에서 선택하기"
    }

    object Toast {
        const val VerifyEmailFailure = "이메일 인증에 실패했어요."
        const val ErrorTakenPhoto = "사진을 불러오지 못했어요.\n다시 시도해 주세요."
        const val NonInstallBrowser = "설치돼 있는 브라우저 앱이 없어요 \uD83D\uDE22"
        const val ImageUploading = "사진을 업로드하고 있어요."
        const val EmployeeIdRegisterRequestDone = "인증 요청이 완료되었어요 :)"
        const val RegisterSuccess = "회원가입이 완료됐어요! \uD83E\uDD73" // 🥳
        const val DuplicateUuid = "동일 계정 내 가입 이력이 존재해요."
        const val DuplicateEmail = "이미 사용중인 이메일이에요."
        const val DuplicateNickname = "랜덤으로 닉네임을 골랐지만 이미 사용중인 닉네임이네요,\n다시 시도해 주세요."
        const val DatabaseError = "서버 데이터베이스에서 에러가 발생했어요."
        const val RegisterNullInformation = "회원가입에 필요한 정보중 일부가 유실되었어요.\n회원가입을 다시 해주세요."
    }

    object Snackbar {
        const val ConfirmFinish = "뒤로가기를 한 번 더 누르시면 종료됩니다."
    }
}
