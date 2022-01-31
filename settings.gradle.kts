enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "RunnerBe"
include(
    ":data",
    ":domain",
    ":presentation",
    ":features:mail",
    ":features:alarm",
    ":features:mypage",
    ":features:writing",
    ":features:snslogin",
    ":features:extrainformation"
)
