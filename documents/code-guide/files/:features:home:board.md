# :features:home:board

러닝 아이템(게시글) 조회 feature 입니다. 위치 기능을 사용하여 현재 위치에 맞는 게시글을 필터링 할 수 있습니다.

# 문제

DFM 에서 resource 에 접근하는 부분에서 문제가 발생하였습니다. 자세한 내용은 [#10](https://github.com/runner-be/RunnerBe-Android/issues/10) 을 확인해 주세요.

---

## #10 - DFM resource 접근

### 문자열 하드코딩

DFM 에서 strings.xml 접근시 리소스 아이디들이 shuffle 되는 문제가 발생함

:presentation 의 strings.xml 에 DFM strings 를 다 정의해 두고 아래와 같이 함수를 만들어 사용할 수 있음

```kotlin
// :presentation.R.string.~~ 불가능 (리소스 참조 불가)

fun Activity.presentationStringOf(name: String) = getString(
    resources.getIdentifier(
        name,
        "string",
        "team.applemango.runnerbe"
    )
)
```

하지만 이는 usage 에서 name 이 하드코딩됨

```kotlin
val title = presentationStringOf(
    when (step) {
        Step.Terms -> "feature_onboard_title_read_terms"
        Step.Birthday -> "feature_onboard_title_input_year"
        Step.Gender -> "feature_onboard_title_select_gender"
        Step.Job -> "feature_onboard_title_whats_job"
        Step.VerifyWithEmail -> "feature_onboard_title_verify_with_email"
        Step.VerifyWithEmployeeID -> "feature_onboard_title_verify_with_employee_id"
        Step.EmailVerifyDone -> "feature_onboard_title_email_verify_done"
        Step.EmployeeIDVerifyRequestDone -> "feature_onboard_title_employee_id_verify_request_done"
    }
)
val subtitle = presentationStringOf(
    when (step) {
        Step.Birthday -> "feature_onboard_subtitle_age_visible_description"
        Step.Job -> "feature_onboard_subtitle_job_can_edit_on_mypage"
        Step.VerifyWithEmail -> "feature_onboard_subtitle_verify_with_email_notice"
        Step.VerifyWithEmployeeID -> "feature_onboard_subtitle_verify_with_employee_id_notice"
        Step.EmailVerifyDone -> "feature_onboard_subtitle_email_verify_done"
        Step.EmployeeIDVerifyRequestDone -> "feature_onboard_subtitle_employee_id_verify_request_done"
        else -> "empty"
    }
)
val bottomCTAButtonText = presentationStringOf(
    when (step) {
        Step.VerifyWithEmail -> "feature_onboard_button_no_email"
        else -> "feature_onboard_button_next"
    }
)
```

하드코딩을 지양하기 위해 임시방편으로 아래와 같은 `StringAsset`을 만들어서 사용중임

```kotlin
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
}
```

아키텍처를 개선하기 위해 DFM 에서 strings.xml 에 참조하는 방법을 연구하여 적용해야 함

- 그 전에 이게 가능한지 먼저 공부 필요
- reference: https://stackoverflow.com/questions/57662892/access-graphics-from-drawable-folder-of-a-dynamic-feature-module-from-main-modul/60227903#60227903
- reference: https://stackoverflow.com/questions/57629292/unable-to-access-resources-of-dynamic-feature-module
- reference: https://stackoverflow.com/questions/6662316/android-getidentifier-doesnt-work-for-string/23989837

### strings.xml

```xml
<string name="empty" />

<string name="feature_onboard_title_read_terms">먼저 이용약관을 읽고\n동의해주세요!</string>
<string name="feature_onboard_title_input_year">출생년도를 입력해주세요.</string>
<string name="feature_onboard_title_select_gender">성별을 선택해주세요.</string>
<string name="feature_onboard_title_whats_job">어떤 직군에서 활동하시나요?</string>
<string name="feature_onboard_title_verify_with_email">회사 이메일로\n직장을 인증해주세요.</string>
<string name="feature_onboard_title_verify_with_employee_id">사진(ex. 사원증, 명함)으로\n직업을 인증해주세요!</string>
<string name="feature_onboard_title_email_verify_done">나를 충분히 소개했어요.\n달릴 준비 완료!</string>
<string name="feature_onboard_title_employee_id_verify_request_done">제출이 완료되었습니다.\n확인 후 알려드릴게요!</string>

<string name="feature_onboard_subtitle_age_visible_description">정확한 나이는 공개되지 않아요!\n20대 초반, 30대 중반 등으로 표기될 거예요.</string>
<string name="feature_onboard_subtitle_job_can_edit_on_mypage">추후 마이페이지에서 수정할 수 있어요!</string>
<string name="feature_onboard_subtitle_verify_with_email_notice">해당 정보는 러너님이 직장인임을 확인하는 용도로만\n사용되며, 외부에 공개되지 않습니다.</string>
<string name="feature_onboard_subtitle_verify_with_employee_id_notice">해당 정보는 러너님이 직장인임을 확인하는 용도로만\n사용되며, 인증 후 안전하게 폐기됩니다.</string>
<string name="feature_onboard_subtitle_email_verify_done">이제 러너비에서 함께 달려볼까요?</string>
<string name="feature_onboard_subtitle_employee_id_verify_request_done">소요 시간은 최대 6시간 정도이며, 완료 시 알림을\n보내드립니다. 그 전까지는 둘러보기만 가능헤요.</string>

<string name="feature_onboard_button_next">다음</string>
<string name="feature_onboard_button_no_email">회사 이메일이 없어요</string>
<string name="feature_onboard_button_verify">인증하기</string>
<string name="feature_onboard_button_start">START!</string>
<string name="feature_onboard_button_goto_main">메인 화면으로</string>

<string name="feature_onboard_content_check_all_terms">모든 약관을 읽었으며, 이에 동의해요.</string>
<string name="feature_onboard_content_check_service_term">[필수] 서비스 이용약관 동의</string>
<string name="feature_onboard_content_check_personal_information_term">[필수] 개인정보 수집/이용 동의</string>
<string name="feature_onboard_content_check_locate_term">[필수] 위치기반 서비스 이용약관 동의</string>

<string name="feature_onboard_gender_female">여성</string>
<string name="feature_onboard_gender_male">남성</string>

<string name="feature_onboard_hint_age_notice">19세 미만은 이용할 수 없어요!</string>
<string name="feature_onboard_hint_base_email">runnerbee@company.com</string>
<string name="feature_onboard_hint_sent_verify_link">인증 링크가 발송되었어요\n메일이 오지 않는다면 스팸 메일함도 확인해주세요!</string>
<string name="feature_onboard_hint_already_use_email">이미 사용 중인 이메일이에요!</string>
<string name="feature_onboard_hint_require_field_job">👉   직장명, 직무/직위는 꼭 드러나야 해요!</string>
<string name="feature_onboard_hint_require_field_information">👉   정보를 식별할 수 있어야 해요.</string>
<string name="feature_onboard_hint_require_field_protect">👉   개인정보 보호를 위해 다른 정보는 가려주세요.</string>

<string name="feature_onboard_dialog_title_verify_notice">인증 확인까지 최대 6시간 정도가\n소요될 수 있어요!</string>
<string name="feature_onboard_dialog_camera">촬영하기</string>
<string name="feature_onboard_dialog_take_photo">앨범에서 선택하기</string>
```

---

### drawable도 문제가 있어 위 방법과 유사한 방법으로 대체함

```kotlin
rememberDrawablePainter(presentationDrawableOf("ic_logo_symbol"))
```