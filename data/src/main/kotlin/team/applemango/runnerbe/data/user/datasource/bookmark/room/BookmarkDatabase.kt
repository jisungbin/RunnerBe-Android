/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BookmarkDatabase.kt] created by Ji Sungbin on 22. 3. 15. 오후 9:58
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.user.datasource.bookmark.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BookmarkPostId::class], version = 1)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun dao(): BookmarkDao

    companion object {
        private var database: BookmarkDatabase? = null

        fun instance(context: Context) = database ?: synchronized(BookmarkDatabase::class) {
            Room.databaseBuilder(
                context.applicationContext,
                BookmarkDatabase::class.java,
                "bookmark-database"
            ).build()
        }.also { instance ->
            database = instance
        }
    }
}
