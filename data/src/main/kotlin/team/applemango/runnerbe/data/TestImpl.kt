package team.applemango.runnerbe.data

import kotlinx.coroutines.delay
import team.applemango.runnerbe.domain.Test
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestImpl @Inject constructor(
    private val one: Int,
) : Test {
    override suspend fun testRequest(): Result<String> {
        delay(2000)
        return Result.success((one + 2).toString())
    }
}
