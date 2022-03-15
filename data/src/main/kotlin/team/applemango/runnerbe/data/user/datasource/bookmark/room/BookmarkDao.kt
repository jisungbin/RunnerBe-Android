/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BookmarkDao.kt] created by Ji Sungbin on 22. 3. 15. 오후 9:06
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.user.datasource.bookmark.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(bookmarkPostId: BookmarkPostId)

    @Delete
    suspend fun delete(bookmarkPostId: BookmarkPostId)

    @Query("SELECT * FROM BookmarkPostId")
    suspend fun getAll(): List<BookmarkPostId>
}
