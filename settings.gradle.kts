enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "RunnerBe"
include(
    ":data",
    ":domain",
    ":shared",
    ":presentation",
    ":features:mail",
    ":features:alarm",
    ":features:board",
    ":features:mypage",
    ":features:writing",
    ":features:snslogin",
    ":features:favorite",
    ":features:extrainformation"
)
