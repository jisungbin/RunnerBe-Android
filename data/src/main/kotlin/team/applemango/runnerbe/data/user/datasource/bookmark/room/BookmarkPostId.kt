/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BookmarkEntity.kt] created by Ji Sungbin on 22. 3. 15. 오후 9:33
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.user.datasource.bookmark.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookmarkPostId(
    @PrimaryKey val postId: Int,
)
