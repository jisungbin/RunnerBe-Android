```kotlin
fun main() {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_MONTH, 20)
    val date = listOf(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH) + 1,
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    println(date)
}

// result: [2022, 4, 6]
```