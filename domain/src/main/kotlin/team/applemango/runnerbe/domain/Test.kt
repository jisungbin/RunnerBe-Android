package team.applemango.runnerbe.domain

interface Test {
    suspend fun testRequest(): Result<String>
}
