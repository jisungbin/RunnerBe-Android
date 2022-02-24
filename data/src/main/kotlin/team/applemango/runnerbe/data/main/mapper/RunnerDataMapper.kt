/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunnerDataMapper.kt] created by Ji Sungbin on 22. 2. 25. 오전 4:40
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.main.mapper

import team.applemango.runnerbe.data.main.model.runner.RunnerData
import team.applemango.runnerbe.domain.main.model.runner.Runner
import team.applemango.runnerbe.domain.runner.AgeGroup
import team.applemango.runnerbe.domain.runner.AgeGroupType
import team.applemango.runnerbe.domain.runner.Diligence
import team.applemango.runnerbe.domain.runner.Gender
import team.applemango.runnerbe.domain.runner.Job
import team.applemango.runnerbe.shared.domain.requireFieldExceptionMessage

internal fun RunnerData.toDomain() = Runner(
    id = requireNotNull(userId) { requireFieldExceptionMessage("userId") },
    nickname = requireNotNull(nickName) { requireFieldExceptionMessage("nickName") },
    gender = Gender.values().first {
        it.code == requireNotNull(gender) { requireFieldExceptionMessage("gender") }
    },
    job = Job.values().first {
        it.string == requireNotNull(job) { requireFieldExceptionMessage("job") }
    },
    profileImageUrl = requireNotNull(profileImageUrl) { requireFieldExceptionMessage("profileImageUrl") },
    ageGroup = run {
        val ageGroup = requireNotNull(age) { requireFieldExceptionMessage("age") }
        val (ageGroupString, ageGroupTypeString) = ageGroup.split(" ")
        AgeGroup(
            ageLevel = ageGroupString.split("대")[0].toInt(),
            type = AgeGroupType.values().first { it.string == ageGroupTypeString }
        )
    },
    diligence = Diligence.values().first {
        it.message == requireNotNull(diligence) { requireFieldExceptionMessage("diligence") }
    }
)
