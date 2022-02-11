/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [PhotoEditor.kt] created by Ji Sungbin on 22. 2. 11. 오후 8:57
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.xml.photoeditor

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout

class PhotoEditor {

    lateinit var editor: PhotoEditor

    fun init(context: Context) {
        val layout = LayoutInflater.from(context)
            .inflate(R.layout.layout_photoeditor, ConstraintLayout(context), false)
        with(layout) {
            val editorView =
        }
    }
}
