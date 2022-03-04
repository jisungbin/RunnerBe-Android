/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunnerDataMapper.kt] created by Ji Sungbin on 22. 2. 25. 오전 4:40
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.runningitem.mapper

import team.applemango.runnerbe.data.runningitem.model.runner.RunnerData
import team.applemango.runnerbe.domain.constant.AgeGroup
import team.applemango.runnerbe.domain.constant.AgeGroupType
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.domain.constant.Job
import team.applemango.runnerbe.domain.constant.Tag
import team.applemango.runnerbe.domain.runningitem.model.runner.Runner
import team.applemango.runnerbe.shared.domain.requireFieldNullMessage

internal fun RunnerData.toDomain() = Runner(
    id = requireNotNull(userId) {
        requireFieldNullMessage("userId")
    },
    nickname = requireNotNull(nickName) {
        requireFieldNullMessage("nickName")
    },
    gender = Gender.values().first {
        it.code == requireNotNull(gender) {
            requireFieldNullMessage("gender")
        }
    },
    job = Job.values().first {
        it.string == requireNotNull(job) {
            requireFieldNullMessage("job")
        }
    },
    profileImageUrl = requireNotNull(profileImageUrl) {
        requireFieldNullMessage("profileImageUrl")
    },
    ageGroup = run {
        val ageGroup = requireNotNull(age) {
            requireFieldNullMessage("age")
        }
        val (ageGroupString, ageGroupTypeString) = ageGroup.split(" ")
        AgeGroup(
            ageLevel = ageGroupString.split("대")[0].toInt(),
            type = AgeGroupType.values().first { it.string == ageGroupTypeString }
        )
    },
    tag = Tag.values().first {
        it.message == requireNotNull(diligence) {
            requireFieldNullMessage("diligence")
        }
    }
)
