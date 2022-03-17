안녕하세요! 스플래시에서 데이터를 prefetch 해주고 있는데, 이렇게 저장한 데이터를 코틀린의 object 클래스에 저장하고 있습니다.
이때 불러오는 데이터의 목적은 메인 화면에서 아래 사진처럼 보여질 리스트들의 데이터 입니다.

<img src="https://user-images.githubusercontent.com/40740128/158814398-97b76bbb-b279-49aa-bad6-1fdd6a8741b1.png" width="33%" />

이 데이터들을 저장하는 object 클래스는 이렇게 생겼습니다.

```kotlin
object MainBoardDataStore {
    private val _runningItems = MutableStateFlow<List<RunningItem>>(emptyList())
    val runningItems = _runningItems.asStateFlow()

    fun updateRunningItems(items: List<RunningItem>) {
        _runningItems.value = items
    }
}
```

`RunningItem` 은 아래처럼 생긴 평범한 data class 입니다.

```kotlin
data class RunningItem(
    val itemId: Int,
    val ownerId: Int,
    val ownerNickName: String,
    val ownerProfileImageUrl: String,
    val createdAt: Date,
    val bookmarkCount: Int,
    val runningType: RunningItemType,
    val finish: Boolean,
    val maxRunnerCount: Int,
    val title: String,
    val gender: Gender,
    val jobs: List<Job>,
    val ageFilter: AgeFilter,
    val runningTime: Time,
    val locate: Locate,
    val distance: Float,
    val meetingDate: Date,
    val message: String,
    val bookmarked: Boolean,
    val attendance: Boolean,
)
```

여러분들은 스플래시에서 prefetch 한 데이터 저장을 어떻게 하시나요? 전 예전부터 이런식으로 object 에 해주고 있었는데, 사용하면서 너무 찝찝헀어서 여쭤봅니다.
